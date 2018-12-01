package com.example.hema.cableapp.Model;

public class DailyCollectionPlanet {

    private String CustomerName;
    private String MobileNo;
    private String PaidAmt;
    private String PaidAmt1;

    public DailyCollectionPlanet(String customerName, String mobileNo,String paidAmt,String paidAmt1)
    {
//
        this.CustomerName = customerName;
        this.MobileNo = mobileNo;
        this.PaidAmt = paidAmt;
        this.PaidAmt1 = paidAmt1;
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

    public String getPaidAmt() {
        return PaidAmt;
    }

    public void setPaidAmt(String paidAmt) {
        PaidAmt = paidAmt;
    }

    public String getPaidAmt1() {
        return PaidAmt1;
    }

    public void setPaidAmt1(String paidAmt1) {
        PaidAmt1 = paidAmt1;
    }
}
