package book;

public class book {
    private int id;
    private String isbn;
    private String name;
    private String writer;
    private String country;
    private String location;
    private String state;
    private String type_1;
    private String type_2;
    private String type_3;
    private String date;

    @Override
    public String toString() {
        return "book{" +
                "id=" + id +
                ", ISBN='" + isbn + '\'' +
                ", name='" + name + '\'' +
                ", writer='" + writer + '\'' +
                ", country='" + country + '\'' +
                ", location='" + location + '\'' +
                ", state='" + state + '\'' +
                ", type_1='" + type_1 + '\'' +
                ", type_2='" + type_2 + '\'' +
                ", type_3='" + type_3 + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public book() {
    }

    public book(int id, String ISBN, String name, String writer, String country, String location, String state, String type_1, String type_2, String type_3, String date) {
        this.id = id;
        this.isbn = ISBN;
        this.name = name;
        this.writer = writer;
        this.country = country;
        this.location = location;
        this.state = state;
        this.type_1 = type_1;
        this.type_2 = type_2;
        this.type_3 = type_3;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getisbn() {
        return isbn;
    }

    public void setISBN(String ISBN) {
        this.isbn = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType_1() {
        return type_1;
    }

    public void setType_1(String type_1) {
        this.type_1 = type_1;
    }

    public String getType_2() {
        return type_2;
    }

    public void setType_2(String type_2) {
        this.type_2 = type_2;
    }

    public String getType_3() {
        return type_3;
    }

    public void setType_3(String type_3) {
        this.type_3 = type_3;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
