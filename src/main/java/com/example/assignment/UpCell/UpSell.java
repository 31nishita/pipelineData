package com.example.assignment.UpCell;
import com.example.assignment.AssignmentRecord;
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
//    @ManyToOne
//   @JoinColumn(name = "record")
//    //@JoinColumn(name = "order_number")
//   private AssignmentRecord assignmentRecord;


    private String service;
    private String partner;
    private String technology;
    private String projectedValue;
    private String cm;


    private String upSellType;

    @ManyToOne
    @JoinColumn(name = "record", nullable = false)
    private AssignmentRecord assignmentRecord;

}
