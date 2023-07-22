package ru.skypro.web_library.testing.service.employee;

import ru.skypro.web_library.testing.dto.*;
import ru.skypro.web_library.testing.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public interface EmployeeMapDTO {
    static EmployeeDTO fromEmployee(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());
        return employeeDTO;
    }

    static Employee toEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        return employee;
    }

    static EmployeeFullInfo toEmployeeFullInfo(Employee employee) {
        EmployeeFullInfo employeeFullInfo = new EmployeeFullInfo();
        employeeFullInfo.setId(employee.getId());
        employeeFullInfo.setName(employee.getName());
        employeeFullInfo.setSalary(employee.getSalary());
        employeeFullInfo.setPositionName(employee.getPosition().getNamePosition());
        return employeeFullInfo;
    }

    static List<EmployeeDTO> toEmployeeDTOList(List<Employee> employeeList) {
        return employeeList
                .stream()
                .map(EmployeeMapDTO::fromEmployee)
                .collect(Collectors.toList());
    }
}
