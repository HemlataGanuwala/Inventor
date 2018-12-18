package com.example.hema.cableapp.Model;

public class ActiveDeactivePlanet {

    private String ADCustomerNm;
    private String ADSetupboxNo;
    private String ADActiveDeactiveAmt;

    public ActiveDeactivePlanet(String adCustomerNm, String adSetupboxNo, String adactivedeactive)
    {
//
        this.ADCustomerNm = adCustomerNm;
        this.ADSetupboxNo = adSetupboxNo;
        this.ADActiveDeactiveAmt = adactivedeactive;
    }

    public String getADCustomerNm() {
        return ADCustomerNm;
    }

    public void setADCustomerNm(String ADCustomerNm) {
        this.ADCustomerNm = ADCustomerNm;
    }

    public String getADSetupboxNo() {
        return ADSetupboxNo;
    }

    public void setADSetupboxNo(String ADSetupboxNo) {
        this.ADSetupboxNo = ADSetupboxNo;
    }

    public String getADActiveDeactiveAmt() {
        return ADActiveDeactiveAmt;
    }

    public void setADActiveDeactiveAmt(String ADActiveDeactiveAmt) {
        this.ADActiveDeactiveAmt = ADActiveDeactiveAmt;
    }
}
