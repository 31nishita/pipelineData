package com.example.assignment.UpCell;

import com.example.assignment.AssignmentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UpCellRepository extends JpaRepository<UpSell,String> {

List<UpSell> findByOrderNumber(String orderNumber);
  // List<UpSell> findBySalesRegion(String salesRegion);
}
