package com.example.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AssignmentApplication {
	@Autowired

	private AssignmentService assignmentService;


	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}


	public void run(String... args) {
		String filePath = "D:\\PipelineData.csv"; // Provide the actual path to the CSV file
	List<AssignmentRecord> assignmentRecords = assignmentService.readCsv(filePath);


		// Print each record
		for (AssignmentRecord assignmentRecord : assignmentRecords) {
		System.out.println(assignmentRecord);
		}
	}
}