package user;

public class user {
    private int id;
    private String username;
    private String password;
    private String create_time;
    private String numofBook;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getNumofBook() {
        return numofBook;
    }

    public void setNumofBook(String numofBook) {
        this.numofBook = numofBook;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", create_time='" + create_time + '\'' +
                ", numofBook='" + numofBook + '\'' +
                '}';
    }
}
