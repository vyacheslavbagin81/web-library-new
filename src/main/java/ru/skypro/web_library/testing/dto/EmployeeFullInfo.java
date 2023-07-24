package ru.skypro.web_library.testing.dto;

import java.util.Objects;

public class EmployeeFullInfo {
    private int id;
    private String name;
    private int salary;
    private String positionName;

    public EmployeeFullInfo(int id, String name, int salary, String positionName) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.positionName = positionName;
    }

    public EmployeeFullInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeFullInfo that = (EmployeeFullInfo) o;
        return id == that.id && salary == that.salary && name.equals(that.name) && positionName.equals(that.positionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary, positionName);
    }
}
