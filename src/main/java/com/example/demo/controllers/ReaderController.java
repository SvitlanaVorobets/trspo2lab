package com.example.demo.controllers;

import com.example.demo.dao.ReaderDao;
import com.example.demo.models.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reader")
public class ReaderController {
    @Autowired
    ReaderDao readerDao;

    @GetMapping("")
    public ResponseEntity<Iterable<Reader>> findAll() {
        List<Reader> readers = readerDao.findAll();
        if (readerDao.findAll().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(readers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> findById(@PathVariable("id") long id) {
        Optional<Reader> reader = readerDao.findById(id);
        return reader.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Reader> createReader(@RequestBody Reader newReader){
        Reader reader = new Reader();
        reader.setName(newReader.getName());
        reader.setPhone(newReader.getPhone());
        reader.setAge(newReader.getAge());
        return new ResponseEntity<>(readerDao.save(reader), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Reader> updateReader(@PathVariable("id") long id, @RequestBody Reader bodyReader) {
        Optional<Reader> reader = readerDao.findById(id);

        if (reader.isPresent()) {
            Reader newReader = reader.get();
            newReader.setName(bodyReader.getName());
            newReader.setPhone(bodyReader.getPhone());
            newReader.setAge(bodyReader.getAge());
            return new ResponseEntity<>(readerDao.save(newReader), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteReader(@PathVariable("id") long id) {
        try {
            readerDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
