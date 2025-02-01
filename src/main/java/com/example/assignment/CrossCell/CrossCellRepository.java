package com.example.assignment.CrossCell;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrossCellRepository extends JpaRepository<CrossSell,String> {
   // List<CrossSell> findByOrderNumber(Long orderNumber);
   List<CrossSell> findByOrderNumber(String orderNumber);
}
