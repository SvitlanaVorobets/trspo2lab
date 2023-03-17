package com.example.demo.dao;

import com.example.demo.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDao extends JpaRepository<Book, Long>{

    @Query("select t from Book t where t.isBorrowed = false")
    Iterable<Book> findNotBorrowed();

    @Query("select t from Book t where t.isBorrowed = true")
    Iterable<Book> findBorrowed();

    @Query("select t from Book t where t.author.id = :id")
    Iterable<Book> findAllBooksByAuthorId(@Param("id") Long id);
}
