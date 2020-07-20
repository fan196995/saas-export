package com.itheima.domain.company;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fanbo
 * @date 2020/6/28 11:34
 */
public class Company implements Serializable {

    private String id; //VARCHAR(40)ID
    private String name; //varchar(255)公司名称
    private Date expirationDate; //dateTIme到期时间
    private String address; //varchaR(255)公司地址
    private String licenseId; //varchar(255)营业执照-图片
    private String representative; //VARCHaR(255)法人代表
    private String phone; //VARCHAR(255)公司电话
    private String companySize; //varchar(255)公司规模
    private String industry; //vARCHar(255)所属行业
    private String remarks; //varCHAR(255)备注
    private Integer state; //int(2)状态
    private Double balance; //double当前余额
    private String city; //varchar(20)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", expirationDate=" + expirationDate +
                ", address='" + address + '\'' +
                ", licenseId='" + licenseId + '\'' +
                ", representative='" + representative + '\'' +
                ", phone='" + phone + '\'' +
                ", companySize='" + companySize + '\'' +
                ", industry='" + industry + '\'' +
                ", remarks='" + remarks + '\'' +
                ", state=" + state +
                ", balance=" + balance +
                ", city='" + city + '\'' +
                '}';
    }
}
