//
//  RealClient.cpp
//  聊天室
//
//  Created by 胡泽弘 on 2022/10/11.
//

#include <iostream>
#include <stdio.h>
#include <string.h>
#include <limits>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <pthread.h>
#include <string>
#include <unistd.h>
#include <fstream>
#include <unordered_map>
#include <list>
#include <stack>
using namespace std;

#define BUFFER_LEN 1024
#define NAME_LEN 64


list<string> logs;
string name;
string path;
string pre;
string namepath = "/root/code/name/name.txt";
unordered_map<string, int> mycommand;

void init(){
    mycommand["help"] = 0;
    mycommand["re"] = 1;
    mycommand["root"] = 2;
    mycommand["clear"] = 3;
    mycommand["logclear"] = 4;
    mycommand["log"] = 5;
    
    char localpath[256] = "/root/code";
//    getcwd(localpath,256);
    path = (string)localpath + "/data/" + name + ".txt";
    
    string ret;
    filebuf f_buf;
    filebuf r_buf;
    filebuf rw_buf;
    
    ostream mk(f_buf.open(path, ios::app));
    f_buf.close();
    
    istream in(r_buf.open(path, ios::in));
    while(!in.fail()){
        getline(in,ret);
        if(in.eof())break;
        logs.push_front(ret);
    }
    r_buf.close();
    
    ostream out(rw_buf.open(path, ios::out));
    int count = 0;
    stack<string> st;
    for(auto & c : logs){
        st.push(c);
        if(++count == 50)break;
    }
    while(!st.empty()){
        out<<st.top()<<endl;
        st.pop();
    }
    rw_buf.close();
}
void printCom(){
    cout<<"Command List:"<<endl;
    cout<<"\t<re>向上一次私聊的用户发送消息"<<endl;
    cout<<"\t<root>向图书管理员咨询"<<endl;
    cout<<"\t<clear>清屏"<<endl;
    cout<<"\t<to:*>向用户*发送"<<endl;
    cout<<"\t<log>查看聊天记录"<<endl;
    cout<<"\t<logclear>清空聊天记录"<<endl;
}
int roomcommand(string & msg,string & aim){
    string str;
    if(msg[0]!='<'){
        aim.clear();

        return 0;
    }
    
    for(int i = 1, len = (int)msg.size();i < len;i++){
        if(msg[i] == '>'){
            if(mycommand.count(str)){
                int key = mycommand[str];
                if(key == 0)printCom();
                else if(key == 1){
                    aim = pre;
                    msg.erase(0,i+1);
                    return 0;
                }
                else if(key == 2){
                    aim = "root";
                    msg.erase(0,i+1);
                    return 0;
                }
                else if(key == 3)for(int i = 0;i < 80;i++)cout<<endl;
                else if(key == 4){
                    logs.clear();
                    filebuf f_buf;
                    ostream out(f_buf.open(path, ios::out));
                    out<<'\0';
                    f_buf.close();
                }
                else if(key == 5){
                    stack<string> st;
                    for(auto & c : logs)st.push(c);
                    while(!st.empty()){
                        cout<<st.top()<<endl;
                        st.pop();
                    }
                }
                
                return 1;
            }
            else return -1;
        }
        else if(msg[i] == ':'){
            if(str == "to"){
                aim.clear();
                int j = i;
                while(++j < len){
                    if(msg[j] == '>'){
                        msg.erase(0, j+1);
                        return 0;
                    }
                    aim += msg[j];
                }
                return -1;
            }
            else break;
        }
        else str += msg[i];
    }
    return -1;
}


void * msg_recv(void * data){
    int p = *(int *)data;
    string msg_buf;
    int msg_len = 0;
    char buffer[BUFFER_LEN + 1];
    int buffer_len = 0;
    while((buffer_len = (int)recv(p, buffer, BUFFER_LEN, 0)) > 0){
        for(int i =  0;i < buffer_len;i++){
            char c = buffer[i];
            if(msg_len)msg_buf += c;
            else msg_buf = c;
            msg_len++;
            if(c == '\n'){
                cout<<msg_buf;
                filebuf f_buf;
                //写入聊天记录
                ostream out(f_buf.open(path, ios::out));
                out<<msg_buf;
                f_buf.close();
                
                msg_len = 0;
                msg_buf.clear();
            }
        }
        memset(buffer, 0, sizeof(buffer));
    }
    printf("服务器离线!");
    return NULL;
}

int main(){
    //唤起图书查询系统
    system("firefox 192.168.226.130:8080/login.jsp &");
    //初始化socket
    int client_sock = socket(AF_INET, SOCK_STREAM, 0);
    sockaddr_in addr;
    addr.sin_family = AF_INET;
    int server_port = 2143;
    char server_ip[16] = "127.0.0.1";
    addr.sin_port = htons(server_port);
    addr.sin_addr.s_addr = inet_addr(server_ip);
    while(connect(client_sock, (sockaddr *)&addr, sizeof(addr))){};
    cout<<"Connecting......";
    fflush(stdout);
    char state[10] = {0};
    recv(client_sock, state, sizeof(state), 0);
    if((string)state == "OK")cout<<"\r已经进入书友会!"<<endl
        <<"输入<help>查看聊天室指令"<<endl;
    else{
        cout<<"\r书友会已满!"<<endl;
        return 0;
    }
    cout<<"获取用户信息..."<<endl;
    //获取用户信息
    while(1){
        filebuf f_buf;
        istream in(f_buf.open(namepath, ios::in));
        in>>name;
        f_buf.close();
        if(name.size() > 0){
            ostream out(f_buf.open(namepath, ios::out));
            f_buf.close();
            break;
        }
        else sleep(2);
    }
    //发送信息，创建recv线程
    send(client_sock, name.c_str(), name.size(), 0);
    pthread_t recv_thread;
    pthread_create(&recv_thread, NULL, msg_recv, (void *)&client_sock);
    
    init();
    while(1){
        char msg[BUFFER_LEN + NAME_LEN + 3];
        char tmp[BUFFER_LEN + 3];
        scanf("%[^\n]",tmp);
        string str(tmp);
        string aim;
        cin.get();
        if(roomcommand(str, aim) > 0)continue;
        
        if(!aim.empty()){
            sprintf(msg,"<%s>%s",aim.c_str(),str.c_str());
            pre = aim;
        }
        else sprintf(msg,"%s",str.c_str());
        
        int n = strlen(msg);
        if(cin.eof()){
            cin.clear();
            clearerr(stdin);
            continue;
        }
        msg[n++] = '\n';
        msg[n] = '\0';
        
        int sent_len = 0;
        int trans_len = min(n,BUFFER_LEN);
        
        while(n > 0){
            int len = send(client_sock,msg + sent_len,trans_len, 0);
            n -= len;
            sent_len += len;
            trans_len = min(n,BUFFER_LEN);
        }
        memset(msg, 0, sizeof(msg));
    }
    pthread_cancel(recv_thread);
    shutdown(client_sock, 2);
    return 0;
}
