package com.example.assignment.UpCell;
import com.example.assignment.AssignmentRecord;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@NoArgsConstructor
@Entity
@Getter
@Setter
public class UpSell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;


    private String service;
    private String partner;
    private String technology;
    private String projectedValue;
    private String cm;


    private String upSellType;

    @ManyToOne
    @JoinColumn(name = "record", nullable = false)
    @JsonBackReference
    private AssignmentRecord assignmentRecord;

}
