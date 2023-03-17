package com.example.demo.controllers;

import com.example.demo.dao.TypeDao;
import com.example.demo.models.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    TypeDao typeDao;

    @GetMapping("")
    public ResponseEntity<Iterable<Type>> findAll() {
        List<Type> types = typeDao.findAll();
        if (typeDao.findAll().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type> findById(@PathVariable("id") long id) {
        Optional<Type> type = typeDao.findById(id);
        return type.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Type> createType(@RequestBody Type newType){
        Type type = new Type();
        type.setName(newType.getName());
        return new ResponseEntity<>(typeDao.save(type), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Type> updateType(@PathVariable("id") long id, @RequestBody Type bodyType) {
        Optional<Type> type = typeDao.findById(id);

        if (type.isPresent()) {
            Type newType = type.get();
            newType.setName(bodyType.getName());
            return new ResponseEntity<>(typeDao.save(newType), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteType(@PathVariable("id") long id) {
        try {
            typeDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
