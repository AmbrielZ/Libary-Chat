package borrow;

public class bookborrow {
    private String book_id;
    private String reader_id;
    private String create_time ;
    private String return_time;

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getReader_id() {
        return reader_id;
    }

    public void setReader_id(String reader_id) {
        this.reader_id = reader_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }

    public bookborrow() {
    }

    public bookborrow(String book_id, String reader_id, String create_time, String return_time) {
        this.book_id = book_id;
        this.reader_id = reader_id;
        this.create_time = create_time;
        this.return_time = return_time;
    }

    @Override
    public String toString() {
        return "bookborrow{" +
                "book_id='" + book_id + '\'' +
                ", reader_id='" + reader_id + '\'' +
                ", create_time='" + create_time + '\'' +
                ", return_time='" + return_time + '\'' +
                '}';
    }
}
