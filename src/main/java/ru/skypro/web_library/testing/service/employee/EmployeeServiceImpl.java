package ru.skypro.web_library.testing.service.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.web_library.testing.dto.*;
import ru.skypro.web_library.testing.entity.*;
import ru.skypro.web_library.testing.repository.*;
import ru.skypro.web_library.testing.exceptions.ExceptionNoId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ReportRepository reportRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    public EmployeeServiceImpl(EmployeeRepository repository, ReportRepository reportRepository) {
        this.employeeRepository = repository;
        this.reportRepository = reportRepository;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public ReportRepository getReportRepository() {
        return reportRepository;
    }

    @Override
    public void addEmployees(EmployeeDTO employee) {
        employeeRepository.save(EmployeeMapDTO.toEmployee(employee));
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() {
        LOGGER.info("Вызываем метод для получения списка сотрудников");
        List<Employee> employeeList = new ArrayList<>();
        employeeRepository.findAll().forEach(employeeList::add);
        return EmployeeMapDTO.toEmployeeDTOList(employeeList);
    }

    @Override
    public Employee editEmployee(EmployeeDTO employeeDTO) throws ExceptionNoId {
        LOGGER.info("Вызываем метод для изменения данных сотрудника");
        int id = employeeDTO.getId();
        Employee result = employeeRepository.findById(id)
                .orElseThrow(()->{
                    LOGGER.error("Ошибка ExceptionNoId нет сотрудника под id={}", id);
                    return new ExceptionNoId();
                });
        LOGGER.debug("Вызываем сотрудника с id={} из базы данных", id);
        if (!employeeDTO.getName().isBlank()) {
            result.setName(employeeDTO.getName());
        }
        if (employeeDTO.getSalary() > 1) {
            result.setSalary(employeeDTO.getSalary());
        }
        employeeRepository.save(result);
        LOGGER.debug("Сохраняем сотрудника с новыми данными в базу данных");
        return result;
    }

    @Override
    public EmployeeDTO getEmployeeToId(int id) throws ExceptionNoId {
        LOGGER.info("Вызываем сотрудника с id={}", id);
        return employeeRepository.findById(id)
                .map(EmployeeMapDTO::fromEmployee)
                .orElseThrow(()->{
                    LOGGER.error("Ошибка ExceptionNoId нет сотрудника под id={}", id);
                    return new ExceptionNoId();
                });
    }

    @Override
    public List<EmployeeFullInfo> getAllEmployeeFullInfo() {
        LOGGER.info("Вызываем метод для получения списка сотрудников с наименованием отдела");
        return employeeRepository.getEmployeeFullInfo();
    }

    @Override
    public EmployeeFullInfo getAllEmployeeToIdFullInfo(int id) throws ExceptionNoId {
        LOGGER.info("Вызываем метод для получения сотрудника по id={} с наименованием отдела", id);
        return employeeRepository.getAllEmployeeToIdFullInfo(id)
                .orElseThrow(()->{
                    LOGGER.error("Ошибка ExceptionNoId нет сотрудника под id={}", id);
                    return new ExceptionNoId();
                });
    }

    @Override
    public List<EmployeeDTO> getEmployeeByPositionName(String position) {
        LOGGER.info("Вызываем метод для получения списка сотрудников работующих в отделе {}", position);
        String pos = position.toLowerCase();
        if (!position.isBlank()) {
            List<Employee> employeeList = employeeRepository.findEmployeeByPosition_Name(pos);
            LOGGER.debug("Получаем список сотрудников работующих в отделе {} из базы данных", position);
            return EmployeeMapDTO.toEmployeeDTOList(employeeList);
        } else return getAllEmployee();
    }

    @Override
    public List<EmployeeDTO> getEmployeeFromPage(int page) {
        int sizePage = 10;
        LOGGER.info("Вызываем метод для вывода списка сотрудников из базы данных находящихся на {} листе. Размер листа - {}", page, sizePage);
        return employeeRepository.findAll(PageRequest.of(page, sizePage))
                .stream().map(EmployeeMapDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEmployeeToId(int id) throws ExceptionNoId {
        LOGGER.info("Вызываем метод для удаления сотрудника по id {}", id);
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else {
            LOGGER.error("Ошибка ExceptionNoId нет сотрудника под id={}", id);
            throw new  ExceptionNoId();
        }
    }

    @Override
    public void uploadAndSaveEmployees(MultipartFile file) throws IOException {
        LOGGER.info("Вызываем метод для добавления списка сотрудников в базу данных из файла {}", file.getName());
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<EmployeeDTO>> typeReference = new TypeReference<>() {
        };
        List<EmployeeDTO> employeeDTOList = objectMapper.readValue(file.getBytes(), typeReference);
        employeeDTOList.forEach(this::addEmployees);
    }

    public boolean checkEmployees(Employee e1, Employee e2) {
        return e1.equals(e2);
    }
}
