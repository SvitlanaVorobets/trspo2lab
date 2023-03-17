package com.example.demo.dto;

public class NewBookDto {

    private Long id;
    private String title;
    private String isbn;
    private int yearOfBook;
    private boolean isBorrowed;
    private Long author_id;
    private Long publisher_id;
    private Long type_id;

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

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public Long getAuthorId() {
        return author_id;
    }

    public void setAuthorId(Long authorId) {
        this.author_id = authorId;
    }

    public Long getPublisherId() {
        return publisher_id;
    }

    public void setPublisherId(Long publisherId) {
        this.publisher_id = publisherId;
    }

    public Long getTypeId() {
        return type_id;
    }

    public void setTypeId(Long typeId) {
        this.type_id = typeId;
    }
}
