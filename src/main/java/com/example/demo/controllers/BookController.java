package com.example.demo.controllers;

import com.example.demo.dao.AuthorDao;
import com.example.demo.dao.BookDao;
import com.example.demo.dao.PublisherDao;
import com.example.demo.dao.TypeDao;
import com.example.demo.dto.NewBookDto;
import com.example.demo.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookDao bookDao;

    @Autowired
    AuthorDao authorDao;

    @Autowired
    PublisherDao publisherDao;

    @Autowired
    TypeDao typeDao;

    @GetMapping("")
    public ResponseEntity<Iterable<Book>> findAll() {
        return sendList(bookDao.findAll());
    }

    @GetMapping("/not-borrowed")
    public ResponseEntity<Iterable<Book>> findAllNotBorrowed() {
        return sendList(bookDao.findNotBorrowed());
    }

    @GetMapping("/borrowed")
    public ResponseEntity<Iterable<Book>> findAllBorrowed() {
        return sendList(bookDao.findBorrowed());
    }

    @GetMapping("/by-author/{id}")
    public ResponseEntity<Iterable<Book>> findByAuthorId(@PathVariable("id") long id) {
        return sendList(bookDao.findAllBooksByAuthorId(id));
    }
    private ResponseEntity<Iterable<Book>> sendList(Iterable<Book> books){
        if (bookDao.findAll().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") long id) {
        Optional<Book> book = bookDao.findById(id);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@RequestBody NewBookDto newBook){
        Book book = new Book();
        setData(newBook, book);
        return new ResponseEntity<>(bookDao.save(book), HttpStatus.CREATED);
    }

    private void setData(@RequestBody NewBookDto newBook, Book book) {
        book.setTitle(newBook.getTitle());
        book.setIsbn(newBook.getIsbn());
        book.setYearOfBook(newBook.getYearOfBook());
        book.setAuthor(authorDao.findById(newBook.getAuthorId()).get());
        book.setPublisher(publisherDao.findById(newBook.getPublisherId()).get());
        book.setType(typeDao.findById(newBook.getTypeId()).get());
        book.setBorrowed(newBook.isBorrowed());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody NewBookDto newBook) {
        Optional<Book> tempBook = bookDao.findById(id);

        if (tempBook.isPresent()) {
            Book book = tempBook.get();
            setData(newBook, book);
            return new ResponseEntity<>(bookDao.save(book), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") long id) {
        try {
            bookDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
