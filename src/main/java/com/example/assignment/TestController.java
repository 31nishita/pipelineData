package com.example.assignment;

import com.example.assignment.CrossCell.CrossCellRepository;
import com.example.assignment.CrossCell.CrossSell;
import com.example.assignment.UpCell.UpCellRepository;
import com.example.assignment.UpCell.UpSell;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

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

    @GetMapping("/assignmentRecords")
    public AssignmentRecord getassignmentRecordsByOrderNumber(String orderNumber) {
        String filePath = "D:\\PipelineData.csv"; // Provide the actual path to the CSV file
        List<AssignmentRecord> assignmentRecords = assignmentService.readCsv(filePath);
        return assignmentRepository.findByOrderNumber(orderNumber);
    }

    //public AssignmentRecord getassignmentRecords(@RequestParam(name = "orderNumber") String orderNumber) {
    // return assignmentService.getRecordsByOrderNumber(orderNumber);


    @Autowired
    private CrossCellRepository crossCellRepository;

    @Autowired
    private UpCellRepository upCellRepository;


    @GetMapping("/print-records")
    public String printAllRecords() {
        List<AssignmentRecord> assignmentRecords = assignmentService.readCsv("D:\\PipelineData.csv");

        // Ensure only up to 677 records are printed
        int recordCount = Math.min(677, assignmentRecords.size());
        for (int i = 0; i < recordCount; i++) {
            System.out.println("Record #" + (i + 1) + ": " + assignmentRecords.get(i));
        }

        return "Successfully printed " + recordCount + " records to the console.";
    }


    @PostMapping("/create")
    public AssignmentRecord createAssignmentRecord(@RequestBody AssignmentRecord assignmentRecord) {
        return assignmentRepository.save(assignmentRecord);
    }

    @PostMapping("/crossCells")
    public ResponseEntity<?> addCrossSells() {
        String filePath = "D:\\PipelineData.csv"; // Provide the actual path to the CSV file
        List<AssignmentRecord> assignmentRecords = assignmentService.readCsv(filePath);

        // crossCellRepository.saveAll(crossSells);
        return ResponseEntity.ok("CrossSells saved successfully.");
    }

    @PostMapping("/{orderNumber}/crossCells")
    public List<CrossSell> addCrossSellsToOrderNumber(
            @PathVariable String orderNumber,
            @RequestBody List<CrossSell> crossSells) {

        AssignmentRecord assignmentRecord = assignmentRepository.findByOrderNumber(orderNumber);


        // Set the reference to the AssignmentRecord for each CrossCell
        //crossSells.forEach(crossSell -> crossSell.setAssignmentRecord(assignmentRecord));

        return crossCellRepository.saveAll(crossSells);
    }

    @PostMapping("/upCells")
    public ResponseEntity<?> addUpSells(@RequestBody List<UpSell> UpSells) {
        upCellRepository.saveAll(UpSells);
        return ResponseEntity.ok("UpSells saved successfully.");
    }


    @PostMapping("/{orderNumber}/upSells")
    public List<UpSell> addUpSellsToOrder(
            @PathVariable String orderNumber,
            @RequestBody List<UpSell> upSells) {

        AssignmentRecord assignmentRecord = assignmentRepository.findByOrderNumber(orderNumber);


        // Set the reference to the AssignmentRecord for each UpCell
        //upSells.forEach(upSell -> upSell.setAssignmentRecord(assignmentRecord));

        return upCellRepository.saveAll(upSells);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCsv(@RequestParam("file") MultipartFile file) {
        try {
            // Save the file locally
            File csvFile = new File("D:\\PipelineData.csv") ;
            file.transferTo(csvFile);

            // Process the file
            List<AssignmentRecord> records = assignmentService.readCsv(csvFile.getAbsolutePath());
            return ResponseEntity.ok("Successfully processed " + 677 + " records.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error processing file: " + e.getMessage());
        }
    }

}




//    @Autowired
//    private CrossSellUpSellService crossSellUpSellService;
//
//    @PostMapping("/{orderNumber}/crossSells")
//    public ResponseEntity<?> addCrossSells(@PathVariable String orderNumber, @RequestBody List<CrossSell> crossSells) {
//        crossSellUpSellService.addCrossSells(orderNumber, crossSells);
//        return ResponseEntity.ok("Cross-sells added to order " + orderNumber);
//    }
//
//    @PostMapping("/{orderNumber}/upsells")
//    public ResponseEntity<?> addUpSells(@PathVariable String orderNumber, @RequestBody List<UpSell> upSells) {
//        crossSellUpSellService.addUpSells(orderNumber, upSells);
//        return ResponseEntity.ok("Up-sells added to order " + orderNumber);
//    }
//
//    @GetMapping("/{orderNumber}/crosssells")
//    public ResponseEntity<List<CrossSell>> getCrossSells(@PathVariable String orderNumber) {
//        List<CrossSell> crossSells = crossSellUpSellService.getCrossSellsByOrderNumber(orderNumber);
//        return ResponseEntity.ok(crossSells);
//    }
//
//    @GetMapping("/{orderNumber}/upsells")
//    public ResponseEntity<List<UpSell>> getUpSells(@PathVariable String orderNumber) {
//        List<UpSell> upSells = crossSellUpSellService.getUpSellsByOrderNumber(orderNumber);
//        return ResponseEntity.ok(upSells);
//    }
//









