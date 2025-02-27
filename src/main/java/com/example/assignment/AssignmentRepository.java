package com.example.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import java.util.List;

@Repository

public interface AssignmentRepository extends JpaRepository<AssignmentRecord,String> {
    AssignmentRecord findByOrderNumber(String orderNumber);

   List<AssignmentRecord >findBySalesRegion(String salesRegion);

   // Fetch dynamically based on a generic column
        @Query("SELECT a FROM AssignmentRecord a WHERE " +
                "(:type = 'salesRegion' AND a.salesRegion = :value) OR " +
                "(:type = 'orderNumber' AND a.orderNumber = :value)")
        List<AssignmentRecord> findByType(@Param("type") String type, @Param("value") String value);
    }




