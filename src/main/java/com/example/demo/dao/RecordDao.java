package com.example.demo.dao;

import com.example.demo.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordDao extends JpaRepository<Record, Long> {

    @Query("select t from Record t where t.returnDate < :day")
    Iterable<Record> findRecordLate(@Param("day") String day);
}
