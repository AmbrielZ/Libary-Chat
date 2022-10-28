//
//  RealServer.cpp
//  聊天室
//
//  Created by 胡泽弘 on 2022/10/10.
//

#include <stdio.h>
#include <iostream>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <pthread.h>
#include <string>
#include <queue>
#include <vector>
#include <unordered_map>
using namespace std;
#define PORT 2143
#define MAX_CLIENT 32
#define BUFFER_LEN 1024
#define NAME_LEN 64

class Reader{
public:
    Reader(int i,int st,char * Name):id(i),socket(st),name(Name){
    }
    int id;
    int socket;
    string name;
};


unordered_map<string, int> namehash;
vector<Reader*> readers(MAX_CLIENT,NULL);
queue<string> msg_q[MAX_CLIENT];

int cur_c_num = 0;

pthread_mutex_t num_mutex;
int tmp = pthread_mutex_init(&num_mutex, NULL);

pthread_t chat_thread[MAX_CLIENT] = {0};
pthread_t send_thread[MAX_CLIENT] = {0};

pthread_mutex_t Mutex[MAX_CLIENT] = {0};
pthread_cond_t cv[MAX_CLIENT] = {0};

void *message_send(void * data){
    Reader * p = (Reader *)data;
    while(1){
        pthread_mutex_lock(&Mutex[p->id]);
        while(msg_q[p->id].empty())pthread_cond_wait(&cv[p->id], &Mutex[p->id]);
        while(!msg_q[p->id].empty()){
            string msg_buf = msg_q[p->id].front();
            int n = (int)msg_buf.size();
            int trans_len = BUFFER_LEN > n ? n : BUFFER_LEN;
            while(n > 0){
                int len = (int)send(p->socket,msg_buf.c_str(),trans_len, 0);
                if(len < 0)return NULL;
                n -= len;
                msg_buf.erase(0, len);
                trans_len = BUFFER_LEN > n ? n : BUFFER_LEN;
            }
            msg_buf.clear();
            msg_q[p->id].pop();
        }
        pthread_mutex_unlock(&Mutex[p->id]);
    }
    return NULL;
}

void message_recv(void * data){
    Reader * p = (Reader *)data;
    string msg_buf;
    int msg_len = 0;
    char buffer[BUFFER_LEN + 1];
    int buf_len = 0;
    int target = -1;
    while((buf_len = (int)recv(p->socket, buffer, BUFFER_LEN, 0)) > 0){
        for(int i = 0;i < buf_len;i++){
            if(!msg_len){
                string str = p->name;
                if(buffer[i] == '<'){
                    i++;
                    string aim;
                    while(i < buf_len && buffer[i] != '>')aim += buffer[i++];
                    i++;
                    if(namehash.count(aim)){
                        str += "->" + aim;
                        target = namehash[aim];
                    }
                    else target = -2;
                }
                str += ':';
                msg_buf = str;
                msg_len = (int)msg_buf.length();
            }
            msg_buf += buffer[i];
            msg_len++;
            if(buffer[i] == '\n'){
                if(target == -1){
                    for(int j = 0;j < MAX_CLIENT;j++){
                        if(readers[j]){
                            pthread_mutex_lock(&Mutex[j]);
                            msg_q[j].push(msg_buf);
                            pthread_cond_signal(&cv[j]);
                            pthread_mutex_unlock(&Mutex[j]);
                        }
                    }
                }
                else if(target == -2){
                    pthread_mutex_lock(&Mutex[p->id]);
                    msg_q[p->id].push("该用户离线或不存在\n");
                    pthread_cond_signal(&cv[p->id]);
                    pthread_mutex_unlock(&Mutex[p->id]);
                }
                else{
                    if(readers[target]){
                        pthread_mutex_lock(&Mutex[target]);
                        msg_q[target].push(msg_buf);
                        pthread_cond_signal(&cv[target]);
                        pthread_mutex_unlock(&Mutex[target]);
                    }
                }
                target = -1;
                msg_len = 0;
                msg_buf.clear();
            }
        }
        buf_len = 0;
        memset(buffer, 0, sizeof(buffer));
    }
}
void *chat(void *data){
    Reader * p = (Reader*)data;
    string hello = "欢迎进入书友会" + p->name + "\n";
    pthread_mutex_lock(&Mutex[p->id]);
    msg_q[p->id].push(hello);
    pthread_cond_signal(&cv[p->id]);
    pthread_mutex_unlock(&Mutex[p->id]);
    hello = "书友 "+ p->name+" 加入，当前共有"+ to_string(cur_c_num) +"人\n";
    for(int i = 0;i < MAX_CLIENT;i++){
        Reader * c =  readers[i];
        if(c && c->id != p->id){
            pthread_mutex_lock(&Mutex[i]);
            msg_q[i].push(hello);
            pthread_cond_signal(&cv[i]);
            pthread_mutex_unlock(&Mutex[i]);
        }
    }
    pthread_create(&send_thread[p->id], NULL, message_send, (void *)p);
    message_recv(data);
    pthread_mutex_lock(&num_mutex);
    cur_c_num--;
    pthread_mutex_unlock(&num_mutex);
    namehash.erase(p->name);
    cout<<p->name<<"离开了.当前人数为:"<<cur_c_num<<endl;
    string bye = p->name + "离开了.当前人数为:" + to_string(cur_c_num) + '\n';
    for (int j = 0; j < MAX_CLIENT; j++){
        if(readers[j] == p)readers[j] = NULL;
        if(readers[j]){
            pthread_mutex_lock(&Mutex[j]);
            msg_q[j].push(bye);
            pthread_cond_signal(&cv[j]);
            pthread_mutex_unlock(&Mutex[j]);
        }
    }
    pthread_mutex_destroy(&Mutex[p->id]);
    pthread_cond_destroy(&cv[p->id]);
    pthread_cancel(send_thread[p->id]);
    return NULL;
}



int main(int argc,char ** argv){
    //创建结构体
    struct sockaddr_in sad;
    int welcomeSocket;//设置欢迎Socket和连接Socket两个Socket;
    welcomeSocket = socket(PF_INET, SOCK_STREAM, 0);
    memset((char*)&sad,0,sizeof(sad));
    sad.sin_family = AF_INET;
    sad.sin_addr.s_addr = INADDR_ANY;
    sad.sin_port = htons(PORT);
    printf("Server is RUNNING on Port %d!\n",PORT);
    //bind
    bind(welcomeSocket, (struct sockaddr*)&sad, sizeof(sad));
    printf("Server bind!\n");
    //listen
    listen(welcomeSocket, 32);
    printf("Server listen!\n");
    while(1){
        int connectionSocket = accept(welcomeSocket, NULL, NULL);
        if(cur_c_num >= MAX_CLIENT){
            if(send(connectionSocket,"FULL", strlen("FULL"),0) < 0)perror("send");
            shutdown(connectionSocket, 2);
            continue;
        }
        else{
            if(send(connectionSocket, "OK", strlen("OK"),0) < 0)perror("send");
        }
        char name[NAME_LEN + 1] = {0};
        ssize_t state = recv(connectionSocket, name, NAME_LEN, 0);
        if(state < 0){
            perror("recv");
            shutdown(connectionSocket, 2);
            continue;
        }
        else if(state == 0){
            shutdown(connectionSocket, 2);
            continue;
        }
        for(int i = 0;i < MAX_CLIENT;i++){
            if(!readers[i]){
                pthread_mutex_lock(&num_mutex);
                readers[i] = new Reader(i,connectionSocket,name);
                namehash[name] = i;
                int cur;
                cur = pthread_mutex_init(&Mutex[i], NULL);
                cur = pthread_cond_init(&cv[i], NULL);
                cur_c_num++;
                pthread_mutex_unlock(&num_mutex);
                pthread_create(&chat_thread[i], NULL, chat, (void*)readers[i]);
                printf("%s 加入聊天室. 当前在线人数为: %d\n", readers[i]->name.c_str(), cur_c_num);
                break;
            }
        }
    }
    // close socket
    for(int i = 0; i < MAX_CLIENT; i++)if(readers[i])shutdown(readers[i]->socket, 2);
    shutdown(welcomeSocket, 2);
    return 0;
}
