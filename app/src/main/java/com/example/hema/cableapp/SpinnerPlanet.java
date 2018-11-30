package com.example.hema.cableapp;

public class SpinnerPlanet {

    private String PackageRate;
    private String Packagename;

    public SpinnerPlanet(String packagename,String packageRate)
    {
//
        this.Packagename = packagename;
        this.PackageRate = packageRate;
    }

    public String getPackagename() {
        return Packagename;
    }

    public void setPackagename(String packagename) {
        Packagename = packagename;
    }

    public String getPackageRate() {
        return PackageRate;
    }

    public void setPackageRate(String packageRate) {
        PackageRate = packageRate;
    }
}
