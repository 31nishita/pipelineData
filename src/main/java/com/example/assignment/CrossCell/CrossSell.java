package com.example.assignment.CrossCell;


import com.example.assignment.AssignmentRecord;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Setter

public class CrossSell{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;

//    @ManyToOne
//    @JoinColumn(name = "record")
//    //@JoinColumn(name = "order_number")
//    private AssignmentRecord assignmentRecord;

    private String service;
    private String partner;
    private String technology;
    private String projectedValue;
    private String cm;

    private String crossSellType;

    @ManyToOne
    @JoinColumn(name = "record", nullable = false)

    @JsonBackReference
    private AssignmentRecord assignmentRecord;



}





