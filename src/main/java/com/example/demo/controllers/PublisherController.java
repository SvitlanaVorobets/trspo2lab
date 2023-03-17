package com.example.demo.controllers;

import com.example.demo.dao.PublisherDao;
import com.example.demo.models.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/publisher")
public class PublisherController {
    @Autowired
    PublisherDao publisherDao;

    @GetMapping("")
    public ResponseEntity<Iterable<Publisher>> findAll() {
        List<Publisher> publishers = publisherDao.findAll();
        if (publisherDao.findAll().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(publishers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> findById(@PathVariable("id") long id) {
        Optional<Publisher> publisher = publisherDao.findById(id);
        return publisher.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher newPublisher){
        Publisher publisher = new Publisher();
        publisher.setName(newPublisher.getName());
        publisher.setCountry(newPublisher.getCountry());
        return new ResponseEntity<>(publisherDao.save(publisher), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable("id") long id, @RequestBody Publisher bodyPublisher) {
        Optional<Publisher> publisher = publisherDao.findById(id);

        if (publisher.isPresent()) {
            Publisher newPublisher = publisher.get();
            newPublisher.setName(bodyPublisher.getName());
            newPublisher.setCountry(newPublisher.getCountry());
            return new ResponseEntity<>(publisherDao.save(newPublisher), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePublisher(@PathVariable("id") long id) {
        try {
            publisherDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
