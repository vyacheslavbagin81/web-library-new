package ru.skypro.web_library.testing.service.employee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.skypro.web_library.testing.dto.EmployeeDTO;
import ru.skypro.web_library.testing.entity.Employee;
import ru.skypro.web_library.testing.exceptions.ExceptionNoId;
import ru.skypro.web_library.testing.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.skypro.web_library.testing.test_data.TestData.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repositoryMok;

    @InjectMocks
    private EmployeeServiceImpl employeeService;


    // метод дабавляет сотрудника в базу
    @Test
    @DisplayName("тест метода который дабавляет сотрудника в базу")
    void addEmployees() {
        employeeService.addEmployees(employeeDTOTest);
        verify(repositoryMok, times(1)).save(any());
    }

    // метод возвращает полный список сотрудников
    @Test
    @DisplayName("тест метода который возвращает полный список сотрудников")
    void getAllEmployee() {
        when(repositoryMok.findAll()).thenReturn(employeeListTest);
        assertEquals(employeeDTOListTest, employeeService.getAllEmployee());
    }

    // метод для изменения данных сотрудника
    @ParameterizedTest
    @MethodSource("dataForTesting_editEmployee")
    @DisplayName("тест метода для изменения данных сотрудника " +
            "передаем корректный id")
    void editEmployee_correctId(EmployeeDTO employeeDTO, Employee employee, Employee employeeOutput) throws ExceptionNoId {
        when(repositoryMok.findById(1)).thenReturn(Optional.of(employeeOutput));
        assertEquals(employee, employeeService.editEmployee(employeeDTO));
    }

    @Test
    @DisplayName("тест метода для изменения данных сотрудника " +
            "передаем некорректный id")
    void editEmployee_notId() throws ExceptionNoId {
        when(repositoryMok.findById(1)).thenThrow(ExceptionNoId.class);
        assertThrows(ExceptionNoId.class, () -> employeeService.editEmployee(employeeDTOTest));
    }

    // возвращает сотрудника по id
    @Test
    @DisplayName("тест метода который возвращает сотрудника по id " +
            "передаем верный id")
    void getEmployeeToId_correctId() throws ExceptionNoId {
        when(repositoryMok.findById(1)).thenReturn(Optional.of(employeeTest));
        assertEquals(employeeDTOTest.getSalary(), employeeService.getEmployeeToId(1).getSalary());
    }

    @Test
    @DisplayName("тест метода который возвращает сотрудника по id " +
            "передаем неверный id")
    void getEmployeeToId_NotId() throws ExceptionNoId {
        when(repositoryMok.findById(25)).thenThrow(ExceptionNoId.class);
        assertThrows(ExceptionNoId.class, () -> employeeService.getEmployeeToId(25));
    }

    // возвращает всех сотрудников с должностями
    @Test
    @DisplayName("тест метода который возвращает всех сотрудников с должностями")
    void getAllEmployeeFullInfo() {
        when(repositoryMok.getEmployeeFullInfo()).thenReturn(List.of(employeeFullInfoTest));
        assertEquals(employeeFullInfoTest, employeeService.getAllEmployeeFullInfo().get(0));

    }

    // возвращает сотрудника по id с должностью
    @Test
    @DisplayName("тест метода который возвращает возвращает сотрудника по id с должностью " +
            "передаем корректный id")
    void getAllEmployeeToIdFullInfo_correctId() throws ExceptionNoId {
        when(repositoryMok.getAllEmployeeToIdFullInfo(1)).thenReturn(Optional.of(employeeFullInfoTest));
        assertEquals(employeeFullInfoTest, employeeService.getAllEmployeeToIdFullInfo(1));
    }

    @Test
    @DisplayName("тест метода который возвращает возвращает сотрудника по id с должностью " +
            "передаем некорректный id")
    void getAllEmployeeToIdFullInfo_notId() throws ExceptionNoId {
        when(repositoryMok.getAllEmployeeToIdFullInfo(1)).thenThrow(ExceptionNoId.class);
        assertThrows(ExceptionNoId.class, () -> employeeService.getAllEmployeeToIdFullInfo(1));
    }

    // метод возвращает список сотрудников по позиции
    @Test
    @DisplayName("тест метода который возвращает список сотрудников по позиции " +
            "передаем корректное имя позиции")
    void getEmployeeByPositionName_correctName() {
        when(repositoryMok.findEmployeeByPosition_Name("joker")).thenReturn(employeeListTest);
        assertEquals(employeeDTOListTest, employeeService.getEmployeeByPositionName("joker"));
    }

    @Test
    @DisplayName("тест метода который возвращает список сотрудников по позиции " +
            "передаем корректное имя позиции с большой буквой")
    void getEmployeeByPositionName_upLowerName() {
        when(repositoryMok.findEmployeeByPosition_Name("joker")).thenReturn(employeeListTest);
        assertEquals(employeeDTOListTest, employeeService.getEmployeeByPositionName("Joker"));
    }

    @Test
    @DisplayName("тест метода который возвращает список сотрудников по позиции " +
            "передаем пустую строку")
    void getEmployeeByPositionName_isBlankName() {
        when(repositoryMok.findAll()).thenReturn(employeeListTest);
        assertEquals(employeeDTOListTest, employeeService.getEmployeeByPositionName(""));
    }

    // метод возвращает информацию о сотрудниках, основываясь на номере страницы.
    @Test
    @DisplayName("тест метода который возвращает информацию о сотрудниках, основываясь на номере страницы.")
    void getEmployeeFromPage() {
        Page<Employee> employees = new PageImpl<>(employeeListTest);
        when(repositoryMok.findAll(PageRequest.of(1, 10))).thenReturn(employees);
        assertEquals(employeeDTOListTest, employeeService.getEmployeeFromPage(1));
    }

    // метод удаляет сотрудника по id
    @Test
    @DisplayName("тест метода который удаляет сотрудника по id " +
            "передаем корректный id")
    void deleteEmployeeToId_correctId() throws ExceptionNoId {
        when(repositoryMok.existsById(1)).thenReturn(true);
        employeeService.deleteEmployeeToId(1);
        verify(repositoryMok, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("тест метода который удаляет сотрудника по id " +
            "передаем некорректный id")
    void deleteEmployeeToId_notId() throws ExceptionNoId {
        when(repositoryMok.existsById(1)).thenReturn(false);
        assertThrows(ExceptionNoId.class, () -> employeeService.deleteEmployeeToId(1));
        verify(repositoryMok, times(0)).deleteById(1);
    }


    private static Stream<Arguments> dataForTesting_editEmployee() {
        return Stream.of(
                Arguments.of(new EmployeeDTO(1, "", 11),
                        new Employee(1, "Ivan", 11, positionTest),
                        new Employee(1, "Ivan", 56, positionTest)),
                Arguments.of(new EmployeeDTO(1, "Jon", 0),
                        new Employee(1, "Jon", 56, positionTest),
                        new Employee(1, "Ivan", 56, positionTest)),
                Arguments.of(new EmployeeDTO(1, "Jon", 111),
                        new Employee(1, "Jon", 111, positionTest),
                        new Employee(1, "Ivan", 56, positionTest))
        );
    }

}