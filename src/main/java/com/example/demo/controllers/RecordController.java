package com.example.demo.controllers;

import com.example.demo.dao.BookDao;
import com.example.demo.dao.ReaderDao;
import com.example.demo.dao.RecordDao;
import com.example.demo.dto.NewRecordDto;
import com.example.demo.models.Book;
import com.example.demo.models.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    RecordDao recordDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    ReaderDao readerDao;

    @GetMapping("")
    public ResponseEntity<Iterable<Record>> findAll() {
        List<Record> records = recordDao.findAll();
        if (recordDao.findAll().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Record> findById(@PathVariable("id") long id) {
        Optional<Record> record = recordDao.findById(id);
        return record.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/late")
    public ResponseEntity<Iterable<Record>> isLate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return new ResponseEntity<>(recordDao.findRecordLate(dateFormat.format(date)), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Record> createRecord(@RequestBody NewRecordDto newRecord){
        Record record = new Record();
        System.out.println(newRecord);
        Book book = bookDao.findById(newRecord.getBook_id()).get();
        if(book.isBorrowed()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        book.setBorrowed(true);
        sendData(record, newRecord);
        return new ResponseEntity<>(recordDao.save(record), HttpStatus.CREATED);
    }

    private void sendData(Record record, @RequestBody NewRecordDto newRecord){
        record.setBook(bookDao.findById(newRecord.getBook_id()).get());
        record.setReader(readerDao.findById(newRecord.getReader_id()).get());
        record.setBorrowDate(newRecord.getBorrow_date());
        record.setReturnDate(newRecord.getReturn_date());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Record> updateRecord(@PathVariable("id") long id, @RequestBody NewRecordDto newRecord) {
        Optional<Record> tempRecord = recordDao.findById(id);

        if (tempRecord.isPresent()) {
            Record record = tempRecord.get();
            sendData(record, newRecord);
            return new ResponseEntity<>(recordDao.save(record), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteRecord(@PathVariable("id") long id) {
        try {
            recordDao.findById(id).get().getBook().setBorrowed(false);
            recordDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
