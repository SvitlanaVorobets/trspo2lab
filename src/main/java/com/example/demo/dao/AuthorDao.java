package com.example.demo.dao;

import com.example.demo.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorDao extends JpaRepository<Author, Long>{
}
