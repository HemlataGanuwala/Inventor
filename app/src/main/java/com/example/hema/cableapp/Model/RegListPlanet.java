package com.example.hema.cableapp.Model;

public class RegListPlanet {

    private String CustomerId;
    private String CustomerName;
    private String MobileNo;
    private String Area;
    private String Setupbox;
    private String Status;

    public RegListPlanet(String customerId,String customerName, String mobileNo, String area, String setupbox,String status)
    {
//
        this.CustomerId = customerId;
        this.CustomerName = customerName;
        this.MobileNo = mobileNo;
        this.Area = area;
        this.Setupbox = setupbox;
        this.Status = status;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getSetupbox() {
        return Setupbox;
    }

    public void setSetupbox(String setupbox) {
        Setupbox = setupbox;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
