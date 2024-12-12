package com.example.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AssignmentRepository extends JpaRepository<AssignmentRecord,String> {
    AssignmentRecord findByOrderNumber(String orderNumber);

}


