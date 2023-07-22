package ru.skypro.web_library.testing.service.employee;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.web_library.testing.dto.EmployeeDTO ;
import ru.skypro.web_library.testing.dto.EmployeeFullInfo ;
import ru.skypro.web_library.testing.exceptions.ExceptionNoId ;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    // метод дабавляет сотрудника в базу
    void addEmployees(EmployeeDTO employee);

    // метод возвращает полный список сотрудников
    List<EmployeeDTO> getAllEmployee();

    // метод для изменения данных сотрудника
    void editEmployee(EmployeeDTO employeeDTO) throws ExceptionNoId;

    // возвращает сотрудника по id
    EmployeeDTO getEmployeeToId(int id) throws ExceptionNoId;

    // возвращает всех сотрудников с должностями
    List<EmployeeFullInfo> getAllEmployeeFullInfo();

    // возвращает сотрудника по id с должностью
    EmployeeFullInfo getAllEmployeeToIdFullInfo(int id) throws ExceptionNoId;

    // метод возвращает список сотрудников по позиции
    List<EmployeeDTO> getEmployeeByPositionName(String position);

    // метод возвращает информацию о сотрудниках, основываясь на номере страницы.
    List<EmployeeDTO> getEmployeeFromPage(int page);

    // метод удаляет сотрудника по id
    void deleteEmployeeToId(int id) throws ExceptionNoId;



//    работа с файлами

    /* метод должен принимать на вход файл JSON, содержащий список сотрудников в JSON-формате.
    Все сотрудники из загружаемого файла должны быть сохранены в базе данных.*/
    void uploadAndSaveEmployees(MultipartFile file) throws IOException;
}
