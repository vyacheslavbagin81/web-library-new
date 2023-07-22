package ru.skypro.web_library.testing.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.web_library.testing.dto.EmployeeDTO;
import ru.skypro.web_library.testing.dto.EmployeeFullInfo;
import ru.skypro.web_library.testing.exceptions.ExceptionNoId;
import ru.skypro.web_library.testing.service.employee.EmployeeService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // метод дабавляет сотрудника в базу
    @PostMapping
    void addEmployees(@RequestBody EmployeeDTO employee) {
        employeeService.addEmployees(employee);
    }

    // метод возвращает полный список сотрудников
    @GetMapping
    List<EmployeeDTO> getAllEmployee() {
        return employeeService.getAllEmployee();
    }

    // метод для изменения данных сотрудника
    @PutMapping
    void editEmployee(@RequestBody EmployeeDTO employeeDTO) throws ExceptionNoId {
        employeeService.editEmployee(employeeDTO);
    }

    // возвращает сотрудника по id
    @GetMapping("/{id}")
    EmployeeDTO getEmployeeToId(@PathVariable int id) throws ExceptionNoId {
        return employeeService.getEmployeeToId(id);
    }

    // возвращает всех сотрудников с должностями
    @GetMapping("/full_info")
    List<EmployeeFullInfo> getAllEmployeeFullInfo() {
        return employeeService.getAllEmployeeFullInfo();
    }


    // возвращает сотрудника по id с должностью
    @GetMapping("/full_info/{id}")
    EmployeeFullInfo getAllEmployeeToIdFullInfo(@PathVariable int id) throws ExceptionNoId {
        return employeeService.getAllEmployeeToIdFullInfo(id);
    }

    // метод возвращает список сотрудников по позиции
    @GetMapping("/position/{position}")
    List<EmployeeDTO> getEmployeeByPositionName(@PathVariable String position) {
        return employeeService.getEmployeeByPositionName(position);
    }

    // метод возвращает информацию о сотрудниках, основываясь на номере страницы.
    @GetMapping("page/{page}")
    List<EmployeeDTO> getEmployeeFromPage(@PathVariable int page) {
        return employeeService.getEmployeeFromPage(page);
    }

    // метод удаляет сотрудника по id
    @DeleteMapping("/{id}")
    void deleteEmployeeToId(@PathVariable int id) throws ExceptionNoId {
        employeeService.deleteEmployeeToId(id);
    }

//    работа с файлами

/* POST-запрос localhost:8080/employees/upload
Он должен принимать на вход файл JSON, содержащий список сотрудников в JSON-формате. Все сотрудники из загружаемого файла должны быть сохранены в базе данных.*/

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void uploadAndSaveEmployees(@RequestParam("file") MultipartFile file) throws IOException {
        employeeService.uploadAndSaveEmployees(file);
    }
}
