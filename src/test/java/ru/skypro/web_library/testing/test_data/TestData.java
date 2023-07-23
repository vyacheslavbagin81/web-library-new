package ru.skypro.web_library.testing.test_data;

import ru.skypro.web_library.testing.dto.EmployeeDTO;
import ru.skypro.web_library.testing.dto.EmployeeFullInfo;
import ru.skypro.web_library.testing.dto.ReportDTO;
import ru.skypro.web_library.testing.entity.Employee;
import ru.skypro.web_library.testing.entity.Position;
import ru.skypro.web_library.testing.entity.ReportFile;
import ru.skypro.web_library.testing.service.employee.EmployeeMapDTO;

import java.util.ArrayList;
import java.util.List;

public class TestData {
    public static final Position positionTest =
            new Position(1, "joker");
    public static final Employee employeeTest =
            new Employee(1, "Ivan", 56, positionTest);
    public static final EmployeeDTO employeeDTOTest = new EmployeeDTO(1, "Ivan", 56);
    public static final EmployeeFullInfo employeeFullInfoTest = new EmployeeFullInfo(1, "Ivan", 56, positionTest.getNamePosition());
    public static final List<Employee> employeeListTest = prepareEmployeeListTest();
    public static final List<EmployeeDTO> employeeDTOListTest = prepareEmployeeDTOListTest();
    public static final ReportDTO reportDTOTest = new ReportDTO("Test", 1, 1, 1, 1);
    public static final List<ReportDTO> reportDTOListTest = prepareReportDTOList();
    public static final ReportFile reportFileTest = new ReportFile(2, "src\\report\\" + "report" + 1L + ".json");

    private static List<Employee> prepareEmployeeListTest() {
        List<Employee> result = new ArrayList<>();
        int i = 10;
        while (i > 0) {
            result.add(new Employee(
                    i,
                    employeeTest.getName() + i,
                    employeeTest.getSalary() + i,
                    employeeTest.getPosition()
            ));
            i = i - 1;
        }
        return result;
    }

    private static List<EmployeeDTO> prepareEmployeeDTOListTest() {
        List<EmployeeDTO> result = new ArrayList<>();
        int i = 10;
        while (i > 0) {
            result.add(new EmployeeDTO(
                    i,
                    employeeDTOTest.getName() + i,
                    employeeDTOTest.getSalary() + i
            ));
            i = i - 1;
        }
        return result;
    }

    private static List<ReportDTO> prepareReportDTOList() {
        List<ReportDTO> result = new ArrayList<>();
        int i = 10;
        while (i > 0) {
            result.add(new ReportDTO(
                    reportDTOTest.getDeportmentName() + i,
                    reportDTOTest.getNumberOfEmployees() + i,
                    reportDTOTest.getMaxSalary() + i,
                    reportDTOTest.getMinSalary() + i,
                    reportDTOTest.getAverageSalary() + i
            ));
            i = i - 1;
        }
        return result;
    }

}
