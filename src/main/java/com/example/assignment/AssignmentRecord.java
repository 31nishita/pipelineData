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




    @OneToMany(mappedBy = "assignmentRecord", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CrossSell> crossSells;

    @OneToMany(mappedBy = "assignmentRecord", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UpSell> upSells;


    private String salesOrgID;
    private String salesRegion;
    private String salesSubRegion;
    private String salesState;
    private String salesUnit;
    private String bLOrgID;
    private String bLRegion;
    private String bLSubRegion;
    private String bLDomain;
    private String bLComp;
    private String bLUnit;
    private String acctID;
    private String acctCategory;
    private String accountName;
    private String sector;
    private String industry;
    private String createDate;
    private String closureDate;
    private String closureStage;
    private String startDate;
    private String endDate;
    private String productID;
    private String serviceLine;
    private String service;
    private String techID;
    private String partner;
    private String technology;
    private String serviceMode;
    private String orderType;
    private String serviceType;
    private String engagementType;
    private String projectValue;
    private String margin;
    private String cm;
    //getter and setter


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSalesRegion() {
        return salesRegion;
    }

    public void setSalesRegion(String salesRegion) {
        this.salesRegion = salesRegion;
    }

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

    public String getSalesOrgID() {
        return salesOrgID;
    }

    public void setSalesOrgID(String salesOrgID) {
        this.salesOrgID = salesOrgID;
    }

    public String getSalesSubRegion() {
        return salesSubRegion;
    }

    public void setSalesSubRegion(String salesSubRegion) {
        this.salesSubRegion = salesSubRegion;
    }

    public String getSalesState() {
        return salesState;
    }

    public void setSalesState(String salesState) {
        this.salesState = salesState;
    }

    public String getSalesUnit() {
        return salesUnit;
    }

    public void setSalesUnit(String salesUnit) {
        this.salesUnit = salesUnit;
    }

    public String getbLOrgID() {
        return bLOrgID;
    }

    public void setbLOrgID(String bLOrgID) {
        this.bLOrgID = bLOrgID;
    }

    public String getbLRegion() {
        return bLRegion;
    }

    public void setbLRegion(String bLRegion) {
        this.bLRegion = bLRegion;
    }

    public String getbLSubRegion() {
        return bLSubRegion;
    }

    public void setbLSubRegion(String bLSubRegion) {
        this.bLSubRegion = bLSubRegion;
    }

    public String getbLDomain() {
        return bLDomain;
    }

    public void setbLDomain(String bLDomain) {
        this.bLDomain = bLDomain;
    }

    public String getbLComp() {
        return bLComp;
    }

    public void setbLComp(String bLComp) {
        this.bLComp = bLComp;
    }

    public String getbLUnit() {
        return bLUnit;
    }

    public void setbLUnit(String bLUnit) {
        this.bLUnit = bLUnit;
    }

    public String getAcctID() {
        return acctID;
    }

    public void setAcctID(String acctID) {
        this.acctID = acctID;
    }

    public String getAcctCategory() {
        return acctCategory;
    }

    public void setAcctCategory(String acctCategory) {
        this.acctCategory = acctCategory;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(String closureDate) {
        this.closureDate = closureDate;
    }

    public String getClosureStage() {
        return closureStage;
    }

    public void setClosureStage(String closureStage) {
        this.closureStage = closureStage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getServiceLine() {
        return serviceLine;
    }

    public void setServiceLine(String serviceLine) {
        this.serviceLine = serviceLine;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTechID() {
        return techID;
    }

    public void setTechID(String techID) {
        this.techID = techID;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(String serviceMode) {
        this.serviceMode = serviceMode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getEngagementType() {
        return engagementType;
    }

    public void setEngagementType(String engagementType) {
        this.engagementType = engagementType;
    }

    public String getProjectValue() {
        return projectValue;
    }

    public void setProjectValue(String projectValue) {
        this.projectValue = projectValue;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getCm() {
        return cm;
    }

    public void setCm(String cm) {
        this.cm = cm;
    }

    public AssignmentRecord(String orderNumber, String salesRegion, List<CrossSell> crossSells, List<UpSell> upSells, String salesOrgID, String salesSubRegion, String salesState, String salesUnit, String bLOrgID, String bLRegion, String bLSubRegion, String bLDomain, String bLComp, String bLUnit, String acctID, String acctCategory, String accountName, String sector, String industry, String createDate, String closureDate, String closureStage, String startDate, String endDate, String productID, String serviceLine, String service, String techID, String partner, String technology, String serviceMode, String orderType, String serviceType, String engagementType, String projectValue, String margin, String cm) {
        this.orderNumber = orderNumber;
        this.salesRegion = salesRegion;
        this.crossSells = crossSells;
        this.upSells = upSells;
        this.salesOrgID = salesOrgID;
        this.salesSubRegion = salesSubRegion;
        this.salesState = salesState;
        this.salesUnit = salesUnit;
        this.bLOrgID = bLOrgID;
        this.bLRegion = bLRegion;
        this.bLSubRegion = bLSubRegion;
        this.bLDomain = bLDomain;
        this.bLComp = bLComp;
        this.bLUnit = bLUnit;
        this.acctID = acctID;
        this.acctCategory = acctCategory;
        this.accountName = accountName;
        this.sector = sector;
        this.industry = industry;
        this.createDate = createDate;
        this.closureDate = closureDate;
        this.closureStage = closureStage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productID = productID;
        this.serviceLine = serviceLine;
        this.service = service;
        this.techID = techID;
        this.partner = partner;
        this.technology = technology;
        this.serviceMode = serviceMode;
        this.orderType = orderType;
        this.serviceType = serviceType;
        this.engagementType = engagementType;
        this.projectValue = projectValue;
        this.margin = margin;
        this.cm = cm;
    }
}