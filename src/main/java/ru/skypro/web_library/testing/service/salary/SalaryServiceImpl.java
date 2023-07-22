package ru.skypro.web_library.testing.service.salary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.web_library.testing.dto.EmployeeDTO;
import ru.skypro.web_library.testing.entity.Employee;
import ru.skypro.web_library.testing.repository.EmployeeRepository;
import ru.skypro.web_library.testing.service.employee.EmployeeMapDTO;

import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {
    private final EmployeeRepository employeeRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(SalaryServiceImpl.class);

    public SalaryServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Integer salarySum() {
        LOGGER.info("Получаем сумму всех з/п");
        return employeeRepository.salarySum();
    }

    @Override
    public Integer salaryAvg() {
        LOGGER.info("Получаем среднюю всех з/п");
        return employeeRepository.salaryAvg();
    }

    @Override
    public EmployeeDTO employeeMinSalary() {
        LOGGER.info("Получаем минимальную з/п");
        return EmployeeMapDTO.fromEmployee(employeeRepository.employeeMinSalary());
    }

    @Override
    public List<EmployeeDTO> withHighestSalary() {
        LOGGER.info("Получаем список сотрудников с максимальной з/п");
        List<Employee> employeeList = employeeRepository.withHighestSalary();
        return EmployeeMapDTO.toEmployeeDTOList(employeeList);
    }

    @Override
    public List<EmployeeDTO> findBySalaryGreaterThan(int salary) {
        LOGGER.info("Получаем список сотрудников с з/п больше {}", salary);
        List<Employee> employeeList = employeeRepository.findBySalaryGreaterThan(salary);
        return EmployeeMapDTO.toEmployeeDTOList(employeeList);
    }
}
