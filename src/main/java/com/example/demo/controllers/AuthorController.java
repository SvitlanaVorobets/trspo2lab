package com.example.demo.controllers;

import com.example.demo.dao.AuthorDao;
import com.example.demo.models.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorDao authorDao;

    @GetMapping("")
    public ResponseEntity<Iterable<Author>> findAll() {
        List<Author> authors = authorDao.findAll();
        if (authorDao.findAll().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable("id") long id) {
        Optional<Author> author = authorDao.findById(id);
        return author.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Author> createAuthor(@RequestBody Author newAuthor){
        Author author = new Author();
        author.setName(newAuthor.getName());
        author.setSurname(newAuthor.getSurname());
        author.setAge(newAuthor.getAge());
        author.setCountry(newAuthor.getCountry());
        return new ResponseEntity<>(authorDao.save(author), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") long id, @RequestBody Author bodyAuthor) {
        Optional<Author> author = authorDao.findById(id);

        if (author.isPresent()) {
            Author newAuthor = author.get();
            newAuthor.setName(bodyAuthor.getName());
            newAuthor.setSurname(bodyAuthor.getSurname());
            newAuthor.setAge(bodyAuthor.getAge());
            newAuthor.setCountry(newAuthor.getCountry());
            return new ResponseEntity<>(authorDao.save(newAuthor), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable("id") long id) {
        try {
            authorDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
