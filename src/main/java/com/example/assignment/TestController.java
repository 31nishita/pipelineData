package com.example.assignment;

import com.example.assignment.CrossCell.CrossCellRepository;
import com.example.assignment.CrossCell.CrossSell;
import com.example.assignment.UpCell.UpCellRepository;
import com.example.assignment.UpCell.UpSell;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TestController {


    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private AssignmentRepository assignmentRepository;

    @GetMapping("/api/testing")
    public String firstMethod() {
        return "testing";
    }

    @GetMapping("/api/getDetails")
    public ResponseEntity<?> getDetailsByOrderNumber(@RequestParam String orderNumber) {
        // Fetch the AssignmentRecord from the database
        AssignmentRecord record = assignmentRepository.findByOrderNumber(orderNumber);

        if (record == null) {
            // If no record is found, return a 404 response
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No data found for the order number: " + orderNumber);
        }

        // Return the record with its associated CrossSell and UpSell data
        return ResponseEntity.ok(record);
    }

    @GetMapping("/assignmentRecords")
    public AssignmentRecord getassignmentRecordsByOrderNumber(String orderNumber) {
        String filePath = "D:\\PipelineData.csv"; // Provide the actual path to the CSV file
        List<AssignmentRecord> assignmentRecords = assignmentService.readCsv(filePath);
        return assignmentRepository.findByOrderNumber(orderNumber);
    }

    @RequestMapping("/records")

    @GetMapping
    public ResponseEntity<Map<String, Object>> getRecordByOrderNumber(@RequestParam String orderNumber) {
        Map<String, Object> response = assignmentService.getFilteredRecordByOrderNumber(orderNumber);
        return ResponseEntity.ok(response);
    }


@GetMapping("/getRecordsBySalesRegion")
public ResponseEntity<List<Map<String, Object>>> getRecordsBySalesRegion(@RequestParam String salesRegion) {
    List<Map<String, Object>> response = assignmentService.getRecordsBySalesRegion(salesRegion);
    return ResponseEntity.ok(response);
}
    @GetMapping("/getRecordsBySalesRegionWithOrderNumber")
    public ResponseEntity<List<String>> getOrderNumberBySalesRegion(@RequestParam String salesRegion) {
        List<String> orderNumbers = assignmentService.getOrderNumbersBySalesRegion(salesRegion);
        return ResponseEntity.ok(orderNumbers);
    }
}



































































//    @Autowired
//    private CrossCellRepository crossCellRepository;
//
//    @Autowired
//    private UpCellRepository upCellRepository;
//
//
//
//
//    @PostMapping("/create")
//    public AssignmentRecord createAssignmentRecord(@RequestBody AssignmentRecord assignmentRecord) {
//        return assignmentRepository.save(assignmentRecord);
//    }
//
//    @PostMapping("/crossCells")
//    public ResponseEntity<?> addCrossSells() {
//        String filePath = "D:\\PipelineData.csv"; // Provide the actual path to the CSV file
//        List<AssignmentRecord> assignmentRecords = assignmentService.readCsv(filePath);
//
//        // crossCellRepository.saveAll(crossSells);
//        return ResponseEntity.ok("CrossSells saved successfully.");
//    }
//
//    @PostMapping("/{orderNumber}/crossCells")
//    public List<CrossSell> addCrossSellsToOrderNumber(
//            @PathVariable String orderNumber,
//            @RequestBody List<CrossSell> crossSells) {
//
//        AssignmentRecord assignmentRecord = assignmentRepository.findByOrderNumber(orderNumber);
//
//
//        // Set the reference to the AssignmentRecord for each CrossCell
//        //crossSells.forEach(crossSell -> crossSell.setAssignmentRecord(assignmentRecord));
//
//        return crossCellRepository.saveAll(crossSells);
//    }
//
//
//    @PostMapping("/upCells")
//    public ResponseEntity<?> addUpSells(@RequestBody List<UpSell> UpSells) {
//        String filePath = "D:\\PipelineData.csv"; // Provide the actual path to the CSV file
//        List<AssignmentRecord> assignmentRecords = assignmentService.readCsv(filePath);
//
//        // crossCellRepository.saveAll(crossSells);
//        return ResponseEntity.ok("UpSells saved successfully.");
//    }
//
//
//    @PostMapping("/{orderNumber}/upSells")
//    public List<UpSell> addUpSellsToOrder(
//            @PathVariable String orderNumber,
//            @RequestBody List<UpSell> upSells) {
//
//        AssignmentRecord assignmentRecord = assignmentRepository.findByOrderNumber(orderNumber);
//
//
//        // Set the reference to the AssignmentRecord for each UpCell
//        //upSells.forEach(upSell -> upSell.setAssignmentRecord(assignmentRecord));
//
//        return upCellRepository.saveAll(upSells);
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadCsv(@RequestParam("file") MultipartFile file) {
//        try {
//            // Save the file locally
//            File csvFile = new File("D:\\PipelineData.csv") ;
//            file.transferTo(csvFile);
//
//            // Process the file
//            List<AssignmentRecord> records = assignmentService.readCsv(csvFile.getAbsolutePath());
//            return ResponseEntity.ok("Successfully processed " + 677 + " records.");
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body("Error processing file: " + e.getMessage());
//        }
//    }
//    @GetMapping("/print-records")
//    public String printAllRecords() {
//        List<AssignmentRecord> assignmentRecords = assignmentService.readCsv("D:\\PipelineData.csv");
//
//        for (AssignmentRecord record : assignmentRecords) {
//            System.out.println("Record: " + record);
//            System.out.println("CrossSells: " + record.getCrossSells());
//            System.out.println("UpSells: " + record.getUpSells());
//        }
//
//        return "Successfully printed " + assignmentRecords.size() + " records to the console.";
//    }

//}










