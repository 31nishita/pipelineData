package com.example.assignment.UpCell;

import com.example.assignment.AssignmentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UpCellRepository extends JpaRepository<UpSell,Long> {
//    List<UpSell> findByOrderNumber(Long orderNumber);
List<UpSell> findByOrderNumber(String orderNumber);
}
