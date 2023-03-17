package com.example.demo.dto;

public class NewRecordDto {
    private Long id;
    private String borrowDate;
    private String returnDate;
    private Long reader_id;
    private Long book_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBorrow_date() {
        return borrowDate;
    }

    public void setBorrow_date(String borrow_date) {
        this.borrowDate = borrow_date;
    }

    public String getReturn_date() {
        return returnDate;
    }

    public void setReturn_date(String return_date) {
        this.returnDate = return_date;
    }

    public Long getReader_id() {
        return reader_id;
    }

    public void setReader_id(Long reader_id) {
        this.reader_id = reader_id;
    }

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    @Override
    public String toString() {
        return "NewRecordDto{" +
                "id=" + id +
                ", borrow_date='" + borrowDate + '\'' +
                ", return_date='" + returnDate + '\'' +
                ", reader_id=" + reader_id +
                ", book_id=" + book_id +
                '}';
    }
}
