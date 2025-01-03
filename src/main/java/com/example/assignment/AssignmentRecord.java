package com.example.assignment;


import com.example.assignment.CrossCell.CrossSell;
import com.example.assignment.UpCell.UpSell;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="record")

@NoArgsConstructor
public class AssignmentRecord {

    @Id
    private String orderNumber;
//    @OneToMany(mappedBy = "assignmentRecord", cascade = CascadeType.ALL)
//    private List<CrossSell> crossSells ;
//
//    @OneToMany(mappedBy = "assignmentRecord", cascade = CascadeType.ALL)
//    private List<UpSell> upSells ;
//
//
@OneToMany(mappedBy = "assignmentRecord", cascade = CascadeType.ALL)
@JsonManagedReference
private List<CrossSell> crossSells;

    @OneToMany(mappedBy = "assignmentRecord", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UpSell> upSells;



    private String SalesOrgID;
    private String SalesRegion;
    private String SalesSubRegion;
    private String SalesState;
    private String SalesUnit;
    private String BLOrgID;
    private String BLRegion;
    private String BLSubRegion;
    private String BLDomain;
    private String BLComp;
    private String BLUnit;
    private String AcctID;
    private String AcctCategory;
    private String AccountName;
    private String Sector;
    private String Industry;
    private String ClosureDate;
    private String ClosureStage;
    private String startDate;
    private String EndDate;
    private String ProductID;
    private String ServiceLine;
    private String Service;
    private String TechID;
    private String Partner;
    private String Technology;
    private String ServiceMode;
    private String OrderType;
    private String ServiceType;
    private String EngagementType;
    private String ProjectValue;
    private String Margin;
    private String CM;
    //getter and setter


    public String getSalesOrgID() {
        return SalesOrgID;
    }

    public String getSalesRegion() {
        return SalesRegion;
    }

    public String getSalesSubRegion() {
        return SalesSubRegion;
    }

    public String getSalesState() {
        return SalesState;
    }

    public String getSalesUnit() {
        return SalesUnit;
    }

    public String getBLOrgID() {
        return BLOrgID;
    }

    public String getBLRegion() {
        return BLRegion;
    }

    public String getBLSubRegion() {
        return BLSubRegion;
    }

    public String getBLDomain() {
        return BLDomain;
    }

    public String getBLComp() {
        return BLComp;
    }

    public String getBLUnit() {
        return BLUnit;
    }

    public String getAcctID() {
        return AcctID;
    }

    public String getAcctCategory() {
        return AcctCategory;
    }

    public String getAccountName() {
        return AccountName;
    }

    public String getSector() {
        return Sector;
    }

    public String getIndustry() {
        return Industry;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getClosureDate() {
        return ClosureDate;
    }

    public String getClosureStage() {
        return ClosureStage;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public String getProductID() {
        return ProductID;
    }

    public String getServiceLine() {
        return ServiceLine;
    }

    public String getService() {
        return Service;
    }

    public String getTechID() {
        return TechID;
    }

    public String getPartner() {
        return Partner;
    }

    public String getTechnology() {
        return Technology;
    }

    public String getServiceMode() {
        return ServiceMode;
    }

    public String getOrderType() {
        return OrderType;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public String getEngagementType() {
        return EngagementType;
    }

    public String getProjectValue() {
        return ProjectValue;
    }

    public String getMargin() {
        return Margin;
    }

    public String getCM() {
        return CM;
    }

    public void setSalesOrgID(String salesOrgID) {
        SalesOrgID = salesOrgID;
    }

    public void setSalesRegion(String salesRegion) {
        SalesRegion = salesRegion;
    }

    public void setSalesSubRegion(String salesSubRegion) {
        SalesSubRegion = salesSubRegion;
    }

    public void setSalesState(String salesState) {
        SalesState = salesState;
    }

    public void setSalesUnit(String salesUnit) {
        SalesUnit = salesUnit;
    }

    public void setBLOrgID(String BLOrgID) {
        this.BLOrgID = BLOrgID;
    }

    public void setBLRegion(String BLRegion) {
        this.BLRegion = BLRegion;
    }

    public void setBLSubRegion(String BLSubRegion) {
        this.BLSubRegion = BLSubRegion;
    }

    public void setBLDomain(String BLDomain) {
        this.BLDomain = BLDomain;
    }

    public void setBLComp(String BLComp) {
        this.BLComp = BLComp;
    }

    public void setBLUnit(String BLUnit) {
        this.BLUnit = BLUnit;
    }

    public void setAcctID(String acctID) {
        AcctID = acctID;
    }

    public void setAcctCategory(String acctCategory) {
        AcctCategory = acctCategory;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public void setSector(String sector) {
        Sector = sector;
    }

    public void setIndustry(String industry) {
        Industry = industry;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setClosureDate(String closureDate) {
        ClosureDate = closureDate;
    }

    public void setClosureStage(String closureStage) {
        ClosureStage = closureStage;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public void setServiceLine(String serviceLine) {
        ServiceLine = serviceLine;
    }

    public void setService(String service) {
        Service = service;
    }

    public void setTechID(String techID) {
        TechID = techID;
    }

    public void setPartner(String partner) {
        Partner = partner;
    }

    public void setTechnology(String technology) {
        Technology = technology;
    }

    public void setServiceMode(String serviceMode) {
        ServiceMode = serviceMode;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public void setEngagementType(String engagementType) {
        EngagementType = engagementType;
    }

    public void setProjectValue(String projectValue) {
        ProjectValue = projectValue;
    }

    public void setMargin(String margin) {
        Margin = margin;
    }

    public void setCM(String CM) {
        this.CM = CM;
    }

//    public List<UpSell> getUpSells() {
//        return upSells;
//    }
//
//    public void setUpSells(List<UpSell> upSells) {
//        this.upSells = upSells;
//    }
//
//    public List<CrossSell> getCrossSells() {
//        return crossSells;
//    }
//
//    public void setCrossSells(List<CrossSell> crossSells) {
//        this.crossSells = crossSells;
//    }


    public List<CrossSell> getCrossSells() {
        return crossSells;
    }

    public void setCrossSells(List<CrossSell> crossSells) {
        this.crossSells = crossSells;
    }

    public List<UpSell> getUpSells() {
        return upSells;
    }

    public void setUpSells(List<UpSell> upSells) {
        this.upSells = upSells;
    }

    public AssignmentRecord(String orderNumber, String salesOrgID, String salesRegion, String salesSubRegion, String salesState, String salesUnit, String BLOrgID, String BLRegion, String BLSubRegion, String BLDomain, String BLComp, String BLUnit, String acctID, String acctCategory, String accountName, String sector, String industry, String closureDate, String closureStage, String startDate, String endDate, String productID, String serviceLine, String service, String techID, String partner, String technology, String serviceMode, String orderType, String serviceType, String engagementType, String projectValue, String margin, String CM) {
        this.orderNumber = orderNumber;
        SalesOrgID = salesOrgID;
        SalesRegion = salesRegion;
        SalesSubRegion = salesSubRegion;
        SalesState = salesState;
        SalesUnit = salesUnit;
        this.BLOrgID = BLOrgID;
        this.BLRegion = BLRegion;
        this.BLSubRegion = BLSubRegion;
        this.BLDomain = BLDomain;
        this.BLComp = BLComp;
        this.BLUnit = BLUnit;
        AcctID = acctID;
        AcctCategory = acctCategory;
        AccountName = accountName;
        Sector = sector;
        Industry = industry;
        ClosureDate = closureDate;
        ClosureStage = closureStage;
        this.startDate = startDate;
        EndDate = endDate;
        ProductID = productID;
        ServiceLine = serviceLine;
        Service = service;
        TechID = techID;
        Partner = partner;
        Technology = technology;
        ServiceMode = serviceMode;
        OrderType = orderType;
        ServiceType = serviceType;
        EngagementType = engagementType;
        ProjectValue = projectValue;
        Margin = margin;
        this.CM = CM;
    }
}
