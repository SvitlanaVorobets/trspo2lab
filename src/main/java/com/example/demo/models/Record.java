package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "record")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnore
    private Book book;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "reader_id", nullable = false)
    @JsonIgnore
    private Reader reader;

    @Column(name = "borrowDate")
    private String borrowDate;

    @Column(name = "returnDate")
    private String returnDate;

    public Record(Long id, Book book, Reader reader, String borrowDate, String returnDate) {
        this.id = id;
        this.book = book;
        this.reader = reader;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Record() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", book=" + book +
                ", reader=" + reader +
                ", borrowDate='" + borrowDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                '}';
    }
}
