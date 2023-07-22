package ru.skypro.web_library.testing.dto;


public class ReportDTO {
    String deportmentName;
    long numberOfEmployees;
    int maxSalary;
    int minSalary;
    double averageSalary;

    public ReportDTO(String deportmentName, long numberOfEmployees, int maxSalary, int minSalary, double averageSalary) {
        this.deportmentName = deportmentName;
        this.numberOfEmployees = numberOfEmployees;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
        this.averageSalary = averageSalary;
    }

    public ReportDTO() {
    }

    public String getDeportmentName() {
        return deportmentName;
    }

    public void setDeportmentName(String deportmentName) {
        this.deportmentName = deportmentName;
    }

    public long getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(long numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(double averageSalary) {
        this.averageSalary = averageSalary;
    }
}
