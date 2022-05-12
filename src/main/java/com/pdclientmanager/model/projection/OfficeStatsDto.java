package com.pdclientmanager.model.projection;

public class OfficeStatsDto {
    private int totalCases;
    private int totalInCustodyCases;
    private double totalAvgAge;
    private double inCustodyAvgAge;
    
    public OfficeStatsDto(int totalCases, int totalInCustodyCases, double totalAvgAge, double inCustodyAvgAge) {
        this.totalCases = totalCases;
        this.totalInCustodyCases = totalInCustodyCases;
        this.totalAvgAge = totalAvgAge;
        this.inCustodyAvgAge = inCustodyAvgAge;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getTotalInCustodyCases() {
        return totalInCustodyCases;
    }

    public void setTotalInCustodyCases(int totalInCustodyCases) {
        this.totalInCustodyCases = totalInCustodyCases;
    }

    public double getTotalAvgAge() {
        return totalAvgAge;
    }

    public void setTotalAvgAge(double totalAvgAge) {
        this.totalAvgAge = totalAvgAge;
    }

    public double getInCustodyAvgAge() {
        return inCustodyAvgAge;
    }

    public void setInCustodyAvgAge(double inCustodyAvgAge) {
        this.inCustodyAvgAge = inCustodyAvgAge;
    }
    
    public double getRoundedTotalAvgAge() {
        return Math.round(totalAvgAge * 100) / 100;
    }
    
    public double getRoundedInCustodyAvgAge() {
        return Math.round(inCustodyAvgAge * 100) / 100;
    }
    
}
