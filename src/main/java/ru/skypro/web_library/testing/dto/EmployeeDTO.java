package ru.skypro.web_library.testing.dto;

import java.io.Serializable;
import java.util.Objects;

public class EmployeeDTO implements Serializable {
    private int id;
    private String name;
    private int salary;

    public EmployeeDTO() {
    }

    public EmployeeDTO(int id, String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDTO that = (EmployeeDTO) o;
        return id == that.id && salary == that.salary && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary);
    }
}
