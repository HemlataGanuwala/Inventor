package com.example.hema.cableapp.Model;

public class ReportPlanet {

    private String ReportCustomerNm;
    private String ReportMobileNo;
    private String ReportPaidAmt;
    private String ReportPaidAmt1;

    public ReportPlanet(String reportCustomerNm, String reportMobileNo, String reportPaidAmt,String reportPaidAmt1)
    {
        this.ReportCustomerNm = reportCustomerNm;
        this.ReportMobileNo = reportMobileNo;
        this.ReportPaidAmt = reportPaidAmt;
        this.ReportPaidAmt1 = reportPaidAmt1;
    }

    public String getReportCustomerNm() {
        return ReportCustomerNm;
    }

    public void setReportCustomerNm(String reportCustomerNm) {
        ReportCustomerNm = reportCustomerNm;
    }

    public String getReportMobileNo() {
        return ReportMobileNo;
    }

    public void setReportMobileNo(String reportMobileNo) {
        ReportMobileNo = reportMobileNo;
    }

    public String getReportPaidAmt() {
        return ReportPaidAmt;
    }

    public void setReportPaidAmt(String reportPaidAmt) {
        ReportPaidAmt = reportPaidAmt;
    }

    public String getReportPaidAmt1() {
        return ReportPaidAmt1;
    }

    public void setReportPaidAmt1(String reportPaidAmt1) {
        ReportPaidAmt1 = reportPaidAmt1;
    }
}
