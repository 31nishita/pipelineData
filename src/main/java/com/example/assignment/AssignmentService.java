package com.example.assignment;

import com.example.assignment.CrossCell.CrossCellRepository;
import com.example.assignment.CrossCell.CrossSell;
import com.example.assignment.UpCell.UpCellRepository;
import com.example.assignment.UpCell.UpSell;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.transaction.Transactional;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private CrossCellRepository crossCellRepository;

    @Autowired
    private UpCellRepository upCellRepository;

    public List<AssignmentRecord> readCsv(String filePath) {
        List<AssignmentRecord> assignmentRecords = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            int rowNumber = 0;

            for (String[] row : csvReader) { // Iterate through rows
                rowNumber++;

                // Skip header row
                if (rowNumber == 1)
                    //  System.out.println("Skipping header row: " + Arrays.toString(row));
                    continue;


                // Validate row length
                if (row.length < 65)
                    //System.out.println("Skipping row #" + rowNumber + " due to insufficient columns: " + Arrays.toString(row));
                    continue;


                try {

                    AssignmentRecord assignmentRecord = new AssignmentRecord();
                    assignmentRecord.setSalesOrgID(row[0]);
                    assignmentRecord.setSalesRegion(row[1]);
                    assignmentRecord.setSalesSubRegion(row[2]);
                    assignmentRecord.setSalesState(row[3]);
                    assignmentRecord.setSalesUnit(row[4]);
                    assignmentRecord.setbLOrgID(row[5]);
                    assignmentRecord.setbLRegion(row[6]);
                    assignmentRecord.setbLSubRegion(row[7]);
                    assignmentRecord.setbLDomain(row[8]);
                    assignmentRecord.setbLComp(row[9]);
                    assignmentRecord.setbLUnit(row[10]);
                    assignmentRecord.setAcctID(row[11]);
                    assignmentRecord.setAcctCategory(row[12]);
                    assignmentRecord.setAccountName(row[13]);
                    assignmentRecord.setSector(row[14]);
                    assignmentRecord.setIndustry(row[15]);
                    assignmentRecord.setOrderNumber(row[16]);
                    assignmentRecord.setCreateDate(row[17]);
                    assignmentRecord.setClosureDate(row[18]);
                    assignmentRecord.setClosureStage(row[19]);
                    assignmentRecord.setStartDate(row[20]);
                    assignmentRecord.setEndDate(row[21]);
                    assignmentRecord.setProductID(row[22]);
                    assignmentRecord.setServiceLine(row[23]);
                    assignmentRecord.setService(row[24]);
                    assignmentRecord.setTechID(row[25]);
                    assignmentRecord.setPartner(row[26]);
                    assignmentRecord.setTechnology(row[27]);
                    assignmentRecord.setServiceMode(row[28]);
                    assignmentRecord.setOrderType(row[29]);
                    assignmentRecord.setServiceType(row[30]);
                    assignmentRecord.setEngagementType(row[31]);
                    assignmentRecord.setProjectValue(row[32]);// [(safeParseDouble(row[31]));
                    assignmentRecord.setMargin(row[33]);//(safeParseDouble(row[32]));
                    assignmentRecord.setCm(row[34]);//(safeParseDouble(row[33]));

                    // Save AssignmentRecord to DB
                    assignmentRecord = assignmentRepository.save(assignmentRecord);
                    assignmentRecords.add(assignmentRecord);

                    // Process CrossSell and UpSell data
                    saveCrossSellData(row, assignmentRecord);
                    saveUpSellData(row, assignmentRecord);

                    System.out.println("Processed row #" + rowNumber + ": " + assignmentRecord);
                } catch (Exception e) {
                    System.out.println("Error processing row #" + rowNumber + ": " + Arrays.toString(row));
                    e.printStackTrace();
                }
            }

            System.out.println("Total records processed: " + assignmentRecords.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return assignmentRepository.findAll(); // Return all records for debugging
    }

    private void saveCrossSellData(String[] row, AssignmentRecord assignmentRecord) {
        try {
            CrossSell crossSell1 = createCrossSell(row[16], row[35], row[36], row[37], row[38], row[39], assignmentRecord, "CrossSell1");
            CrossSell crossSell2 = createCrossSell(row[16], row[40], row[41], row[42], row[43], row[44], assignmentRecord, "CrossSell2");
            CrossSell crossSell3 = createCrossSell(row[16], row[45], row[46], row[47], row[48], row[49], assignmentRecord, "CrossSell3");

            crossCellRepository.saveAll(List.of(crossSell1, crossSell2, crossSell3));
        } catch (Exception e) {
            System.out.println("Error saving CrossSell data for order number: " + row[16]);
            e.printStackTrace();
        }
    }


    private void saveUpSellData(String[] row, AssignmentRecord assignmentRecord) {
        try {
            UpSell upSell1 = createUpSell(row[16], row[50], row[51], row[52], row[53], row[54], assignmentRecord, "UpSell1");
            UpSell upSell2 = createUpSell(row[16], row[55], row[56], row[57], row[58], row[59], assignmentRecord, "UpSell2");
            UpSell upSell3 = createUpSell(row[16], row[60], row[61], row[62], row[63], row[64], assignmentRecord, "UpSell3");

            upCellRepository.saveAll(List.of(upSell1, upSell2, upSell3));
        } catch (Exception e) {
            System.out.println("Error saving UpSell data for order number: " + row[16]);
            e.printStackTrace();
        }
    }

    private CrossSell createCrossSell(String orderNumber, String service, String technology, String partner, String projectedValue, String cm, AssignmentRecord assignmentRecord, String crossSellType) {
        CrossSell crossSell = new CrossSell();
        crossSell.setOrderNumber(orderNumber);
        crossSell.setService(service);
        crossSell.setPartner(partner);
        crossSell.setTechnology(technology);
        crossSell.setProjectedValue(projectedValue);//(safeParseDouble(projectedValue));
        crossSell.setCm(cm);//(safeParseDouble(cm));
        crossSell.setCrossSellType(crossSellType); // Set type
        crossSell.setAssignmentRecord(assignmentRecord);
        return crossSell;
    }


    private UpSell createUpSell(String orderNumber, String service, String technology, String partner, String projectedValue, String cm, AssignmentRecord assignmentRecord, String upSellType) {
        UpSell upSell = new UpSell();
        upSell.setOrderNumber(orderNumber);
        upSell.setService(service);
        upSell.setPartner(partner);
        upSell.setTechnology(technology);
        upSell.setProjectedValue(projectedValue);//(safeParseDouble(projectedValue));
        upSell.setCm(cm);//(safeParseDouble(cm));
        upSell.setUpSellType(upSellType); // Set type
        upSell.setAssignmentRecord(assignmentRecord);
        return upSell;

    }

    private double safeParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric value: " + value);
            return 0.0;
        }
    }


    public Map<String, Object> getFilteredRecordByOrderNumber(String orderNumber) {
        // Fetch the record by order number
        AssignmentRecord record = assignmentRepository.findById(orderNumber)
                .orElseThrow(() -> new RuntimeException("Record not found for order number: " + orderNumber));


        Map<String, Object> response = new HashMap<>();
        response.put("orderNumber", record.getOrderNumber());
        response.put("salesRegion", record.getSalesRegion());
        response.put("productID", record.getProductID());
        response.put("partner", record.getPartner());
        response.put("margin", record.getMargin());
        response.put("projectValue", record.getProjectValue());


        List<Map<String, String>> crossSellDetails = record.getCrossSells().stream().map(crossSell -> {
            Map<String, String> crossSellMap = new HashMap<>();
            crossSellMap.put("orderNumber", record.getOrderNumber());
            crossSellMap.put("service", crossSell.getService());
            crossSellMap.put("technology", crossSell.getTechnology());
            crossSellMap.put("partner", crossSell.getPartner());

            return crossSellMap;
        }).collect(Collectors.toList());
        response.put("crossSellDetails", crossSellDetails);

        // Add UpSell details
        List<Map<String, String>> upSellDetails = record.getUpSells().stream().map(upSell -> {
            Map<String, String> upSellMap = new HashMap<>();
            upSellMap.put("orderNumber", record.getOrderNumber());
            upSellMap.put("service", upSell.getService());
            upSellMap.put("partner", upSell.getPartner());
            upSellMap.put("technology", upSell.getTechnology());
            return upSellMap;
        }).collect(Collectors.toList());
        response.put("upSellDetails", upSellDetails);

        return response;
    }
    public List<Map<String, Object>> getRecordsBySalesRegion(String salesRegion) {
        List<AssignmentRecord> records = assignmentRepository.findBySalesRegion(salesRegion);

        if (records.isEmpty()) {
            throw new RuntimeException("No records found for salesRegion containing: " + salesRegion);
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (AssignmentRecord record : records) {
            Map<String, Object> response = new HashMap<>();
            response.put("salesRegion", record.getSalesOrgID());
        response.put("salesOrgId", record.getSalesRegion());
        response.put("salesSubRegion", record.getSalesSubRegion());
        response.put("salesState", record.getSalesState());
        response.put("salesUnit", record.getSalesUnit());
        response.put("bLOrgId", record.getbLOrgID());
        response.put("bLRegion", record.getbLRegion());
        response.put("bLSubRegion", record.getbLSubRegion());
        response.put("bLDomain", record.getbLDomain());
        response.put("bLComp", record.getbLComp());
        response.put("bLUnit", record.getbLUnit());
        response.put("acctID", record.getAcctID());
        response.put("acctCategory", record.getAcctCategory());
        response.put("accountName", record.getAccountName());
        response.put("industry", record.getIndustry());
        response.put("orderNumber", record.getOrderNumber());
        response.put("createDate", record.getCreateDate());
        response.put("closureDate", record.getClosureDate());
        response.put("closureStage", record.getClosureStage());
        response.put("startDate", record.getStartDate());
        response.put("endDate", record.getEndDate());
        response.put("productID", record.getProductID());
        response.put("serviceLine", record.getServiceLine());
        response.put("service", record.getService());
        response.put("techID", record.getTechID());
        response.put("partner", record.getPartner());
        response.put("technology", record.getTechnology());
        response.put("serviceMode", record.getServiceMode());
        response.put("orderType", record.getOrderType());
        response.put("serviceType", record.getServiceType());
        response.put("engagementType", record.getMargin());
        response.put("projectedValue", record.getProjectValue());
        response.put("margin", record.getIndustry());
        response.put("CM", record.getCm());


            // Fetch CrossSell data
            List<Map<String, String>> crossSellDetails = record.getCrossSells().stream()
                    .map(crossSell -> Map.of(
                            "service", crossSell.getService(),
                            "technology", crossSell.getTechnology(),
                            "partner", crossSell.getPartner(),
                            "projectedValue", crossSell.getProjectedValue(),
                            "cm", crossSell.getCm()))
                    .collect(Collectors.toList());

            response.put("crossSellDetails", crossSellDetails);

            // Fetch UpSell data
            List<Map<String, String>> upSellDetails = record.getUpSells().stream()
                    .map(upSell -> Map.of(
                            "service", upSell.getService(),
                            "technology", upSell.getTechnology(),
                            "partner", upSell.getPartner(),
                            "projectedValue", upSell.getProjectedValue(),
                            "cm", upSell.getCm()))
                    .collect(Collectors.toList());

            response.put("upSellDetails", upSellDetails);

            result.add(response);
        }

        return result;
    }
    public List<String> getOrderNumbersBySalesRegion(String salesRegion) {
        List<AssignmentRecord> records = assignmentRepository.findBySalesRegion(salesRegion);
        if (records.isEmpty()) {
            throw new RuntimeException("No records found for Sales Region: " + salesRegion);
        }
        return records.stream()
                .map(AssignmentRecord::getOrderNumber)
                .collect(Collectors.toList());
    }

}


//    public List<Map<String, Object>> getRecordsByType(String type, String value) {
//        List<AssignmentRecord> records;
//
//        // Dynamically filter based on the 'type' parameter
//        switch (type) {
//            case "salesRegion":
//                records = assignmentRepository.findBySalesRegion(value);
//                break;
//            case "orderNumber":
//                records = (List<AssignmentRecord>) assignmentRepository.findByOrderNumber(value);
//                break;
//            default:
//                throw new IllegalArgumentException("Invalid type: " + type);
//        }
//
//        if (records.isEmpty()) {
//            throw new RuntimeException("No records found for " + type + ": " + value);
//        }
//
//        List<Map<String, Object>> result = new ArrayList<>();
//        for (AssignmentRecord record : records) {
//            Map<String, Object> response = new HashMap<>();
//            response.put("salesRegion", record.getSalesOrgID());
//        response.put("salesOrgId", record.getSalesRegion());
//        response.put("salesSubRegion", record.getSalesSubRegion());
//        response.put("salesState", record.getSalesState());
//        response.put("salesUnit", record.getSalesUnit());
//        response.put("bLOrgId", record.getbLOrgID());
//        response.put("bLRegion", record.getbLRegion());
//        response.put("bLSubRegion", record.getbLSubRegion());
//        response.put("bLDomain", record.getbLDomain());
//        response.put("bLComp", record.getbLComp());
//        response.put("bLUnit", record.getbLUnit());
//        response.put("acctID", record.getAcctID());
//        response.put("acctCategory", record.getAcctCategory());
//        response.put("accountName", record.getAccountName());
//        response.put("industry", record.getIndustry());
//        response.put("orderNumber", record.getOrderNumber());
//        response.put("createDate", record.getCreateDate());
//        response.put("closureDate", record.getClosureDate());
//        response.put("closureStage", record.getClosureStage());
//        response.put("startDate", record.getStartDate());
//        response.put("endDate", record.getEndDate());
//        response.put("productID", record.getProductID());
//        response.put("serviceLine", record.getServiceLine());
//        response.put("service", record.getService());
//        response.put("techID", record.getTechID());
//        response.put("partner", record.getPartner());
//        response.put("technology", record.getTechnology());
//        response.put("serviceMode", record.getServiceMode());
//        response.put("orderType", record.getOrderType());
//        response.put("serviceType", record.getServiceType());
//        response.put("engagementType", record.getMargin());
//        response.put("projectedValue", record.getProjectValue());
//        response.put("margin", record.getIndustry());
//        response.put("CM", record.getCm());
//
//            // Add cross-sell details
//            List<Map<String, String>> crossSellDetails = record.getCrossSells().stream().map(crossSell -> {
//                Map<String, String> crossSellMap = new HashMap<>();
//                crossSellMap.put("service", crossSell.getService());
//                crossSellMap.put("technology", crossSell.getTechnology());
//                crossSellMap.put("partner", crossSell.getPartner());
//                crossSellMap.put("projectedValue", crossSell.getProjectedValue());
//                crossSellMap.put("cm", crossSell.getCm());
//                return crossSellMap;
//            }).collect(Collectors.toList());
//
//            response.put("crossSellDetails", crossSellDetails);
//
//            // Add up-sell details
//            List<Map<String, String>> upSellDetails = record.getUpSells().stream().map(upSell -> {
//                Map<String, String> upSellMap = new HashMap<>();
//                upSellMap.put("service", upSell.getService());
//                upSellMap.put("technology", upSell.getTechnology());
//                upSellMap.put("partner", upSell.getPartner());
//                upSellMap.put("projectedValue", upSell.getProjectedValue());
//                upSellMap.put("cm", upSell.getCm());
//                return upSellMap;
//            }).collect(Collectors.toList());
//
//            response.put("upSellDetails", upSellDetails);
//
//            result.add(response);
//        }
//
//        return result;
//    }
//}

























































































































//    public Map<String, Object> getSalesRegion(String salesRegion) {
//        // Fetch the record by order number
//        AssignmentRecord record = assignmentRepository.findById(salesRegion)
//                .orElseThrow(() -> new RuntimeException("Record not found for order number: " + salesRegion));
//
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("salesRegion", record.getSalesOrgID());
//        response.put("salesOrgId", record.getSalesRegion());
//        response.put("salesSubRegion", record.getSalesSubRegion());
//        response.put("salesState", record.getSalesState());
//        response.put("salesUnit", record.getSalesUnit());
//        response.put("bLOrgId", record.getbLOrgID());
//        response.put("bLRegion", record.getbLRegion());
//        response.put("bLSubRegion", record.getbLSubRegion());
//        response.put("bLDomain", record.getbLDomain());
//        response.put("bLComp", record.getbLComp());
//        response.put("bLUnit", record.getbLUnit());
//        response.put("acctID", record.getAcctID());
//        response.put("acctCategory", record.getAcctCategory());
//        response.put("accountName", record.getAccountName());
//        response.put("industry", record.getIndustry());
//        response.put("orderNumber", record.getOrderNumber());
//        response.put("createDate", record.getCreateDate());
//        response.put("closureDate", record.getClosureDate());
//        response.put("closureStage", record.getClosureStage());
//        response.put("startDate", record.getStartDate());
//        response.put("endDate", record.getEndDate());
//        response.put("productID", record.getProductID());
//        response.put("serviceLine", record.getServiceLine());
//        response.put("service", record.getService());
//        response.put("techID", record.getTechID());
//        response.put("partner", record.getPartner());
//        response.put("technology", record.getTechnology());
//        response.put("serviceMode", record.getServiceMode());
//        response.put("orderType", record.getOrderType());
//        response.put("serviceType", record.getServiceType());
//        response.put("engagementType", record.getMargin());
//        response.put("projectedValue", record.getProjectValue());
//        response.put("margin", record.getIndustry());
//        response.put("CM", record.getCm());
//
//
//
//        List<Map<String, String>> crossSellDetails = record.getCrossSells().stream().map(crossSell -> {
//            Map<String, String> crossSellMap = new HashMap<>();
//            crossSellMap.put("salesRegion", record.getOrderNumber());
//            crossSellMap.put("service", crossSell.getService());
//            crossSellMap.put("technology", crossSell.getTechnology());
//            crossSellMap.put("partner", crossSell.getPartner());
//            crossSellMap.put("projectedValue", crossSell.getProjectedValue());
//            crossSellMap.put("cm", crossSell.getCm());
//            return crossSellMap;
//        }).collect(Collectors.toList());
//        response.put("crossSellDetails", crossSellDetails);
//
//        // Add UpSell details
//        List<Map<String, String>> upSellDetails = record.getUpSells().stream().map(upSell -> {
//            Map<String, String> upSellMap = new HashMap<>();
//            upSellMap.put("salesRegion", record.getOrderNumber());
//            upSellMap.put("service", upSell.getService());
//            upSellMap.put("partner", upSell.getPartner());
//            upSellMap.put("technology", upSell.getTechnology());
//            upSellMap.put("projectedValue", upSell.getProjectedValue());
//            upSellMap.put("cm", upSell.getCm());
//            return upSellMap;
//        }).collect(Collectors.toList());
//        response.put("upSellDetails", upSellDetails);
//
//        return response;
//    }
//}
//
//
////    @Column(name = "sales_region")
////        public List<String, Object>> getRecordsByType(String salesRegion){ //type, String value) {
////            List<AssignmentRecord> records = (List<AssignmentRecord>) assignmentRepository.findBySalesRegion(salesRegion);
////            if (records.isEmpty()) {
////                throw new RuntimeException("No records found for " +salesRegion); //type + ": " + value);
////            }
////
////            List<Map<String, Object>> result = new ArrayList<>();
////
////            for (AssignmentRecord record : records) {
////                Map<String, Object> response = new HashMap<>();
////                response.put("orderNumber", record.getOrderNumber());
////                response.put("salesRegion", record.getSalesRegion());
////                response.put("productID", record.getProductID());
////                response.put("partner", record.getPartner());
////                response.put("margin", record.getMargin());
////                response.put("projectValue", record.getProjectValue());
////                response.put("salesOrgID", record.getSalesOrgID());
////                response.put("salesSubRegion", record.getSalesSubRegion());
////                response.put("salesState", record.getSalesState());
////                response.put("salesUnit", record.getSalesUnit());
////                response.put("BLOrgID", record.getBLOrgID());
////                response.put("BLRegion", record.getBLRegion());
////                response.put("BLSubRegion", record.getBLSubRegion());
////                response.put("BLDomain", record.getBLDomain());
////                response.put("BLComp", record.getBLComp());
////                response.put("BLUnit", record.getBLUnit());
////                response.put("acctID", record.getAcctID());
////                response.put("acctCategory", record.getAcctCategory());
////                response.put("accountName", record.getAccountName());
////                response.put("sector", record.getSector());
////                response.put("industry", record.getIndustry());
////                response.put("closureDate", record.getClosureDate());
////                response.put("closureStage", record.getClosureStage());
////                response.put("startDate", record.getStartDate());
////                response.put("endDate", record.getEndDate());
////                response.put("serviceLine", record.getServiceLine());
////                response.put("service", record.getService());
////                response.put("techID", record.getTechID());
////                response.put("technology", record.getTechnology());
////                response.put("serviceMode", record.getServiceMode());
////                response.put("orderType", record.getOrderType());
////                response.put("serviceType", record.getServiceType());
////                response.put("engagementType", record.getEngagementType());
////                response.put("CM", record.getCM());
////
////                // Fetch CrossSell data
////                List<Map<String, String>> crossSellDetails = crossCellRepository.findBySalesRegion(record.getOrderNumber())
////                        .stream()
////                        .map(crossSell -> {
////                            Map<String, String> crossSellMap = new HashMap<>();
////                           // crossSellMap.put("orderNumber", crossSell.getOrderNumber());
////                            crossSellMap.put("service", crossSell.getService());
////                            crossSellMap.put("technology", crossSell.getTechnology());
////                            crossSellMap.put("partner", crossSell.getPartner());
////                            crossSellMap.put("ProjectedValue", crossSell.getProjectedValue());
////                            crossSellMap.put("Cm", crossSell.getCm());
////                            return crossSellMap;
////                        }).collect(Collectors.toList());
////
////                response.put("crossSellDetails", crossSellDetails);
////
////                // Fetch UpSell data
////                List<Map<String, String>> upSellDetails = upCellRepository.findBySalesRegion(record.getOrderNumber())
////                        .stream()
////                        .map(upSell -> {
////                            Map<String, String> upSellMap = new HashMap<>();
////                           // upSellMap.put("orderNumber", upSell.getOrderNumber());
////                            upSellMap.put("service", upSell.getService());
////                            upSellMap.put("technology", upSell.getTechnology());
////                            upSellMap.put("partner", upSell.getPartner());
////                            upSellMap.put("ProjectedValue",upSell.getProjectedValue());
////                            upSellMap.put("Cm", upSell.getCm());
////                            return upSellMap;
////                        }).collect(Collectors.toList());
////
////                response.put("upSellDetails", upSellDetails);
////
////                result.add(response);
////            }
////
////            return result;
////        }
////    }
//
//
//
//
//
//
//
























































































































































































//import com.example.assignment.CrossCell.CrossCellRepository;
//import com.example.assignment.CrossCell.CrossSell;
//import com.example.assignment.UpCell.UpCellRepository;
//import com.example.assignment.UpCell.UpSell;
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvValidationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static java.lang.Double.parseDouble;
//import static org.hibernate.query.criteria.internal.ValueHandlerFactory.isNumeric;
//
//@Service
//public class AssignmentService {
//    @Autowired
//    private AssignmentRepository assignmentRepository;
//
//    @Autowired
//    private CrossCellRepository crossCellRepository;
//
//    @Autowired
//    private UpCellRepository upCellRepository;
//
//    public List<AssignmentRecord> readCsv(String filePath) {
//        List<AssignmentRecord> assignmentRecords = new ArrayList<>();
//
//        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
//            int rowNumber = 0;
//
//            for (String[] row : csvReader) { // Iterate through rows
//              //  if (rowNumber == 1) {
//                    rowNumber++; // Skip header row
//                  //  continue;
//                }
//
//                if (row.length < 64) { // Check for the required number of columns
//                    System.out.println("Row has fewer columns than expected: " + Arrays.toString(row));
//                    continue;
//                }
//
//                // Create and populate AssignmentRecord
//                AssignmentRecord assignmentRecord = new AssignmentRecord();
//                assignmentRecord.setSalesOrgID(row[0]);
//                assignmentRecord.setSalesRegion(row[1]);
//                assignmentRecord.setSalesSubRegion(row[2]);
//                assignmentRecord.setSalesState(row[3]);
//                assignmentRecord.setSalesUnit(row[4]);
//                assignmentRecord.setBLOrgID(row[5]);
//                assignmentRecord.setBLRegion(row[6]);
//                assignmentRecord.setBLSubRegion(row[7]);
//                assignmentRecord.setBLDomain(row[8]);
//                assignmentRecord.setBLComp(row[9]);
//                assignmentRecord.setBLUnit(row[10]);
//                assignmentRecord.setAcctID(row[11]);
//                assignmentRecord.setAcctCategory(row[12]);
//                assignmentRecord.setAccountName(row[13]);
//                assignmentRecord.setSector(row[14]);
//                assignmentRecord.setIndustry(row[15]);
//                assignmentRecord.setOrderNumber(row[16]);
//                assignmentRecord.setClosureDate(row[17]);
//                assignmentRecord.setClosureStage(row[18]);
//                assignmentRecord.setStartDate(row[19]);
//                assignmentRecord.setEndDate(row[20]);
//                assignmentRecord.setProductID(row[21]);
//                assignmentRecord.setServiceLine(row[22]);
//                assignmentRecord.setService(row[23]);
//                assignmentRecord.setTechID(row[24]);
//                assignmentRecord.setPartner(row[25]);
//                assignmentRecord.setTechnology(row[26]);
//                assignmentRecord.setServiceMode(row[27]);
//                assignmentRecord.setOrderType(row[28]);
//                assignmentRecord.setServiceType(row[29]);
//                assignmentRecord.setEngagementType(row[30]);
//                assignmentRecord.setProjectValue(isNumeric(row[31]) ? parseDouble(row[31]) : 0.0);
//                assignmentRecord.setMargin(isNumeric(row[32]) ? parseDouble(row[32]) : 0.0);
//                assignmentRecord.setCM(isNumeric(row[33]) ? parseDouble(row[33]) : 0.0);
//
//                assignmentRecord = assignmentRepository.save(assignmentRecord);
//                assignmentRecords.add(assignmentRecord);
//
//
//                //Process CrossSell data
//                saveCrossSellData(row, assignmentRecord);
//
//                // Process UpSell data
//                saveUpSellData(row, assignmentRecord);
//            }
//            // Print the 677 records (or all if the size is less than 677)
//            int recordCount = Math.min(677, assignmentRecords.size());
//            for (int i = 0; i < recordCount; i++) {
//                AssignmentRecord record = assignmentRecords.get(i);
//                System.out.println("Record #" + (i + 1) + ": " + record);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        //return assignmentRecords;
//        return assignmentRepository.findAll();
//    }
//
//    private void saveCrossSellData(String[] row, AssignmentRecord assignmentRecord) {
//        CrossSell crossSell1 = createCrossSell(row[16], row[34], row[35], row[36], row[37], row[38], assignmentRecord);
//        CrossSell crossSell2 = createCrossSell(row[16], row[39], row[40], row[41], row[42], row[43], assignmentRecord);
//        CrossSell crossSell3 = createCrossSell(row[16], row[44], row[45], row[46], row[47], row[48], assignmentRecord);
//
//        crossCellRepository.saveAll(List.of(crossSell1, crossSell2, crossSell3));
//    }
//
//    private void saveUpSellData(String[] row, AssignmentRecord assignmentRecord) {
//        UpSell upSell1 = createUpSell(row[16], row[49], row[50], row[51], row[52], row[53], assignmentRecord);
//        UpSell upSell2 = createUpSell(row[16], row[54], row[55], row[56], row[57], row[58], assignmentRecord);
//        UpSell upSell3 = createUpSell(row[16], row[59], row[60], row[61], row[62], row[63], assignmentRecord);
//
//        upCellRepository.saveAll(List.of(upSell1, upSell2, upSell3));
//    }
//
//    private CrossSell createCrossSell(String orderNumber, String service, String technology, String partner, String projectedValue, String cm, AssignmentRecord assignmentRecord) {
//        CrossSell crossSell = new CrossSell();
//        crossSell.setOrderNumber(orderNumber);
//        crossSell.setService(service);
//        crossSell.setTechnology(technology);
//        crossSell.setPartner(partner);
//        crossSell.setProjectedValue(parseDouble(projectedValue));
//        crossSell.setCm(parseDouble(cm));
//        crossSell.setAssignmentRecord(assignmentRecord);
//        return crossSell;
//    }
//
//    private UpSell createUpSell(String orderNumber, String service, String technology, String partner, String projectedValue, String cm, AssignmentRecord assignmentRecord) {
//        UpSell upSell = new UpSell();
//        upSell.setOrderNumber(orderNumber);
//        upSell.setService(service);
//        upSell.setTechnology(technology);
//        upSell.setPartner(partner);
//        upSell.setProjectedValue(parseDouble(projectedValue));
//        upSell.setCm(parseDouble(cm));
//        upSell.setAssignmentRecord(assignmentRecord);
//        return upSell;
//
//
//    }}
////            // Print the 677 records (or all if the size is less than 677)
////            int recordCount = Math.min(677, assignmentRecords.size());
////            for (int i = 0; i < recordCount; i++) {
////                AssignmentRecord record = assignmentRecords.get(i);
////                System.out.println("Record #" + (i + 1) + ": " + record);
////            }
////
////        } catch (IOException e) {
//////            e.printStackTrace();
////        } catch (CsvValidationException e) {
////            e.printStackTrace();
////        }
////
////        return assignmentRecords;
////    }
////}



























































































































































































//package com.example.assignment;
//
//import com.example.assignment.CrossCell.CrossCellRepository;
//import com.example.assignment.CrossCell.CrossSell;
//import com.example.assignment.UpCell.UpCellRepository;
//import com.example.assignment.UpCell.UpSell;
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvValidationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static java.lang.Double.parseDouble;
//import static org.hibernate.query.criteria.internal.ValueHandlerFactory.isNumeric;
//
//@Service
//public class AssignmentService {
//
//    @Autowired
//    private AssignmentRepository assignmentRepository;
//
//    @Autowired
//    private CrossCellRepository crossCellRepository;

//    @Autowired
//    private UpCellRepository upCellRepository;
//    private boolean isNumeric(String str) {
//
//        if (str == null || str.isEmpty()) {
//            return false;
//        }
//        try {
//            Double.parseDouble(str);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }


//    public List<AssignmentRecord> readCsv(String filePath) {
//        List<AssignmentRecord> assignmentRecords = new ArrayList<>();
//
//        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
//            int rowNumber = 0;
//
//            for (String[] row : csvReader) { // Iterate through rows
//                if (rowNumber == 0) {
//                    rowNumber++; // Skip header row
//                    continue;
//                }
//
//                if (row.length < 64) { // Check for the required number of columns
//                    System.out.println("Row has fewer columns than expected: " + Arrays.toString(row));
//                    continue;
//                }
//
//
//                // Create and populate AssignmentRecord
//                AssignmentRecord assignmentRecord = new AssignmentRecord();
//                assignmentRecord.setSalesOrgID(row[0]);
//                assignmentRecord.setSalesRegion(row[1]);
//                assignmentRecord.setSalesSubRegion(row[2]);
//                assignmentRecord.setSalesState(row[3]);
//                assignmentRecord.setSalesUnit(row[4]);
//                assignmentRecord.setBLOrgID(row[5]);
//                assignmentRecord.setBLRegion(row[6]);
//                assignmentRecord.setBLSubRegion(row[7]);
//                assignmentRecord.setBLDomain(row[8]);
//                assignmentRecord.setBLComp(row[9]);
//                assignmentRecord.setBLUnit(row[10]);
//                assignmentRecord.setAcctID(row[11]);
//                assignmentRecord.setAcctCategory(row[12]);
//                assignmentRecord.setAccountName(row[13]);
//                assignmentRecord.setSector(row[14]);
//                assignmentRecord.setIndustry(row[15]);
//                assignmentRecord.setOrderNumber(row[16]);
//                assignmentRecord.setClosureDate(row[17]);
//                assignmentRecord.setClosureStage(row[18]);
//                assignmentRecord.setStartDate(row[19]);
//                assignmentRecord.setEndDate(row[20]);
//                assignmentRecord.setProductID(row[21]);
//                assignmentRecord.setServiceLine(row[22]);
//                assignmentRecord.setService(row[23]);
//                assignmentRecord.setTechID(row[24]);
//                assignmentRecord.setPartner(row[25]);
//                assignmentRecord.setTechnology(row[26]);
//                assignmentRecord.setServiceMode(row[27]);
//                assignmentRecord.setOrderType(row[28]);
//                assignmentRecord.setServiceType(row[29]);
//                assignmentRecord.setEngagementType(row[30]);
//                assignmentRecord.setProjectValue(isNumeric(row[31]) ? parseDouble(row[31]) : 0.0);
//                assignmentRecord.setMargin(isNumeric(row[32]) ? parseDouble(row[32]) : 0.0);
//                assignmentRecord.setCM(isNumeric(row[33]) ? parseDouble(row[33]) : 0.0);
//
//
//                assignmentRecord = assignmentRepository.save(assignmentRecord);
//                assignmentRecords.add(assignmentRecord);
//
//                // Process CrossSell data
//                saveCrossSellData(row, assignmentRecord);
//
//                // Process UpSell data
//                saveUpSellData(row, assignmentRecord);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        return assignmentRecords;
//    }
//
//    private void saveCrossSellData(String[] row, AssignmentRecord assignmentRecord) {
//        CrossSell crossSell1 = createCrossSell(row[16], row[34], row[35], row[36], row[37], row[38], assignmentRecord);
//        CrossSell crossSell2 = createCrossSell(row[16], row[39], row[40], row[41], row[42], row[43], assignmentRecord);
//        CrossSell crossSell3 = createCrossSell(row[16], row[44], row[45], row[46], row[47], row[48], assignmentRecord);
//
//        crossCellRepository.saveAll(List.of(crossSell1, crossSell2, crossSell3));
//    }
//
//    private void saveUpSellData(String[] row, AssignmentRecord assignmentRecord) {
//        UpSell upSell1 = createUpSell(row[16], row[49], row[50], row[51], row[52], row[53], assignmentRecord);
//        UpSell upSell2 = createUpSell(row[16], row[54], row[55], row[56], row[57], row[58], assignmentRecord);
//        UpSell upSell3 = createUpSell(row[16], row[59], row[60], row[61], row[62], row[63], assignmentRecord);
//
//        upCellRepository.saveAll(List.of(upSell1, upSell2, upSell3));
//    }
//
//    private CrossSell createCrossSell(String orderNumber, String service, String technology, String partner, String projectedValue, String cm, AssignmentRecord assignmentRecord) {
//        CrossSell crossSell = new CrossSell();
//        crossSell.setOrderNumber(orderNumber);
//        crossSell.setService(service);
//        crossSell.setTechnology(technology);
//        crossSell.setPartner(partner);
//        crossSell.setProjectedValue(parseDouble(projectedValue));
//        crossSell.setCm(parseDouble(cm));
//        crossSell.setAssignmentRecord(assignmentRecord);
//        return crossSell;
//    }
//
//    private UpSell createUpSell(String orderNumber, String service, String technology, String partner, String projectedValue, String cm, AssignmentRecord assignmentRecord) {
//        UpSell upSell = new UpSell();
//        upSell.setOrderNumber(orderNumber);
//        upSell.setService(service);
//        upSell.setTechnology(technology);
//        upSell.setPartner(partner);
//        upSell.setProjectedValue(parseDouble(projectedValue));
//        upSell.setCm(parseDouble(cm));
//        upSell.setAssignmentRecord(assignmentRecord);
//        return upSell;
//    }
//}
//






























































































































































































//
//import com.example.assignment.CrossCell.CrossCellRepository;
//
//import com.example.assignment.CrossCell.CrossSell;
//import com.example.assignment.UpCell.UpCellRepository;
//import com.example.assignment.UpCell.UpSell;
//
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvValidationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import static java.lang.Double.parseDouble;
//
//
//@Service
//public class AssignmentService {
//
//    @Autowired
//
//    private AssignmentRepository assignmentRepository;
//    private CrossCellRepository crossCellRepository;
//private UpCellRepository upCellRepository;
//
//    public AssignmentRecord getRecordsByOrderNumber(String orderNumber) {
//        return assignmentRepository.findByOrderNumber(orderNumber);
//    }
//        public List<AssignmentRecord> readCsv(String filePath){
//            List<AssignmentRecord> assignmentrecords = new ArrayList<>();
//
//            try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
//                String[] nextRecord;
//                int rowNumber = 0;
//
//                while ((nextRecord = csvReader.readNext()) != null) {
//                    if (rowNumber == 0) {
//                        rowNumber++; // Skip header row
//                        continue;
//                    }
//
//
//                    // Safeguard to avoid index out of bounds error
////                    if (nextRecord.length < 34) {
////                        System.out.println("Row has fewer columns than expected: " + nextRecord.length);
////                        continue; // Skip rows with fewer columns
////                    }
//
//                    AssignmentRecord assignmentRecord = new AssignmentRecord();
//
//
//                    assignmentRecord.setSalesOrgID(nextRecord[0]);
//                    assignmentRecord.setSalesRegion(nextRecord[1]);
//                    assignmentRecord.setSalesSubRegion(nextRecord[2]);
//                    assignmentRecord.setSalesState(nextRecord[3]);
//                    assignmentRecord.setSalesUnit(nextRecord[4]);
//                    assignmentRecord.setBLOrgID(nextRecord[5]);
//                    assignmentRecord.setBLRegion(nextRecord[6]);
//                    assignmentRecord.setBLSubRegion(nextRecord[7]);
//                    assignmentRecord.setBLDomain(nextRecord[8]);
//                    assignmentRecord.setBLComp(nextRecord[9]);
//                    assignmentRecord.setBLUnit(nextRecord[10]);
//                    assignmentRecord.setAcctID(nextRecord[11]);
//                    assignmentRecord.setAcctCategory(nextRecord[12]);
//                    assignmentRecord.setAccountName(nextRecord[13]);
//                    assignmentRecord.setSector(nextRecord[14]);
//                    assignmentRecord.setIndustry(nextRecord[15]);
//                    assignmentRecord.setOrderNumber(nextRecord[16]);
//                    assignmentRecord.setClosureDate(nextRecord[17]);
//                    assignmentRecord.setClosureStage(nextRecord[18]);
//                    assignmentRecord.setStartDate(nextRecord[19]);
//                    assignmentRecord.setEndDate(nextRecord[20]);
//                    assignmentRecord.setProductID(nextRecord[21]);
//                    assignmentRecord.setServiceLine(nextRecord[22]);
//                    assignmentRecord.setService(nextRecord[23]);
//                    assignmentRecord.setTechID(nextRecord[24]);
//                    assignmentRecord.setPartner(nextRecord[25]);
//                    assignmentRecord.setTechnology(nextRecord[26]);
//                    assignmentRecord.setServiceMode(nextRecord[27]);
//                    assignmentRecord.setOrderType(nextRecord[28]);
//                    assignmentRecord.setServiceType(nextRecord[29]);
//                    assignmentRecord.setEngagementType(nextRecord[30]);
////                    assignmentRecord.setProjectValue(nextRecord.length);
////                    assignmentRecord.setMargin(nextRecord.length);
////                    assignmentRecord.setCM(nextRecord.length);
//                    assignmentRecord.setProjectValue(parseDouble(nextRecord[31])); // Replace 31 with actual column index
//                    assignmentRecord.setMargin(parseDouble(nextRecord[32]));       // Replace 32 with actual column index
//                    assignmentRecord.setCM(parseDouble(nextRecord[33]));          // Replace 33 with actual column index
//
//                    assignmentRecord=assignmentRepository.save(assignmentRecord);
//                    assignmentrecords.add(assignmentRecord);
//
//                    List<String> crossSellData = new ArrayList<>();
//                    CrossSell crossSell1 = new CrossSell();
//                    crossSell1.setOrderNumber(nextRecord[16]); // Assuming order number is same
//                    crossSell1.setService(nextRecord[34]);    // Replace with actual column index
//                    crossSell1.setTechnology(nextRecord[35]); // Replace with actual column index
//                    crossSell1.setPartner(nextRecord[36]);    // Replace with actual column index
//                    crossSell1.setProjectedValue(parseDouble(nextRecord[37])); // Replace with actual column index
//                    crossSell1.setCm(parseDouble(nextRecord[38]));
//                  crossSell1.setAssignmentRecord(assignmentRecord);
//
//
//                    CrossSell crossSell2 = new CrossSell();
//                    crossSell2.setOrderNumber(nextRecord[16]); // Assuming order number is same
//                    crossSell2.setService(nextRecord[39]);    // Replace with actual column index
//                    crossSell2.setTechnology(nextRecord[40]); // Replace with actual column index
//                    crossSell2.setPartner(nextRecord[41]);    // Replace with actual column index
//                    crossSell2.setProjectedValue(parseDouble(nextRecord[42])); // Replace with actual column index
//                    crossSell2.setCm(parseDouble(nextRecord[43]));
//                     crossSell2.setAssignmentRecord(assignmentRecord);
//
//
//                    CrossSell crossSell3 = new CrossSell();
//                    crossSell3.setOrderNumber(nextRecord[16]); // Assuming order number is same
//                    crossSell3.setService(nextRecord[44]);    // Replace with actual column index
//                    crossSell3.setTechnology(nextRecord[45]);
//                    crossSell3.setPartner(nextRecord[46]);
//                    crossSell3.setProjectedValue(parseDouble(nextRecord[47]));
//                    crossSell3.setCm(parseDouble(nextRecord[48]));
//                    crossSell3.setAssignmentRecord(assignmentRecord);
//
//
//
//                    crossCellRepository.save(crossSell1);
//                    crossCellRepository.save(crossSell2);
//                    crossCellRepository.save(crossSell3);
//
//                    List<String> upSellData = new ArrayList<>();
//                    UpSell upSell1 = new UpSell();
//                    upSell1.setOrderNumber(nextRecord[16]); // Assuming order number is same
//                    upSell1.setService(nextRecord[49]);    // Replace with actual column index
//                    upSell1.setTechnology(nextRecord[50]); // Replace with actual column index
//                    upSell1.setPartner(nextRecord[51]);    // Replace with actual column index
//                    upSell1.setProjectedValue(parseDouble(nextRecord[52])); // Replace with actual column index
//                    upSell1.setCm(parseDouble(nextRecord[53]));
//                     upSell1.setAssignmentRecord(assignmentRecord);
//
//
//                    UpSell upSell2 = new UpSell();
//                    upSell2.setOrderNumber(nextRecord[16]); // Assuming order number is same
//                    upSell2.setService(nextRecord[54]);    // Replace with actual column index
//                    upSell2.setTechnology(nextRecord[55]); // Replace with actual column index
//                    upSell2.setPartner(nextRecord[56]);    // Replace with actual column index
//                    upSell2.setProjectedValue(parseDouble(nextRecord[57])); // Replace with actual column index
//                    upSell2.setCm(parseDouble(nextRecord[58]));
//                     upSell2.setAssignmentRecord(assignmentRecord);
//
//
//                    UpSell upSell3 = new UpSell();
//                    upSell3.setOrderNumber(nextRecord[16]); // Assuming order number is same
//                    upSell3.setService(nextRecord[59]);    // Replace with actual column index
//                    upSell3.setTechnology(nextRecord[60]);
//                    upSell3.setPartner(nextRecord[61]);
//                    upSell3.setProjectedValue(parseDouble(nextRecord[62]));
//                    upSell3.setCm(parseDouble(nextRecord[63]));
//                    upSell3.setAssignmentRecord(assignmentRecord);
//
//
//
//                    upCellRepository.save(upSell1);
//                    upCellRepository.save(upSell2);
//                    upCellRepository.save(upSell3);
//                }
//            } catch (IOException | CsvValidationException e) {
//                e.printStackTrace();
//            }
//
//
//            return assignmentrecords;
//        }
//
//    }




