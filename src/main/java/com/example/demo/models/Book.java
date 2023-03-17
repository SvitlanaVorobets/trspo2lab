package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")

public class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIgnore
    private Author author;

    @Column(name = "isbn")
    private String isbn; //978-3-16-148410-0

    @Column(name = "year_of_book")
    private int yearOfBook;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    @JsonIgnore
    private Publisher publisher;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    @JsonIgnore
    private Type type;
    @Column(name = "is_borrowed")
    private boolean isBorrowed;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> records = new ArrayList<>();

    public Book(Long id, String title, Author author, String isbn, int year, Publisher publisher, Type type, boolean isBorrowed) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.yearOfBook = year;
        this.publisher = publisher;
        this.type = type;
        this.isBorrowed = isBorrowed;
    }

    public Book() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYearOfBook() {
        return yearOfBook;
    }

    public void setYearOfBook(int yearOfBook) {
        this.yearOfBook = yearOfBook;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author.getName() + '\'' +
                ", isbn='" + isbn + '\'' +
                ", year=" + yearOfBook +
                ", publisher='" + publisher.getName() + '\'' +
                ", type='" + type.getName() + '\'' +
                ", isBorrowed=" + isBorrowed +
                '}';
    }
}
