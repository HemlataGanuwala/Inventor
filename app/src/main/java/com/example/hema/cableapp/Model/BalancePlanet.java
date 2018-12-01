package com.example.hema.cableapp.Model;

public class BalancePlanet {

    private String BalCustomerNm;
    private String BalMobileNo;
    private String BalBalanceAmt;

    public BalancePlanet(String balCustomerNm, String balMobileNo,String balBalanceAmt)
    {
//
        this.BalCustomerNm = balCustomerNm;
        this.BalMobileNo = balMobileNo;
        this.BalBalanceAmt = balBalanceAmt;
    }

    public String getBalCustomerNm() {
        return BalCustomerNm;
    }

    public void setBalCustomerNm(String balCustomerNm) {
        BalCustomerNm = balCustomerNm;
    }

    public String getBalMobileNo() {
        return BalMobileNo;
    }

    public void setBalMobileNo(String balMobileNo) {
        BalMobileNo = balMobileNo;
    }

    public String getBalBalanceAmt() {
        return BalBalanceAmt;
    }

    public void setBalBalanceAmt(String balBalanceAmt) {
        BalBalanceAmt = balBalanceAmt;
    }
}
