package com.example.demo.dao;

import com.example.demo.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDao extends JpaRepository<Type, Long>{
}
