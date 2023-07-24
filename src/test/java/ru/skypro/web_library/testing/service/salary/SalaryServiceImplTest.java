package ru.skypro.web_library.testing.service.salary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.web_library.testing.repository.EmployeeRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.skypro.web_library.testing.test_data.TestData.*;

@ExtendWith(MockitoExtension.class)
class SalaryServiceImplTest {
    @Mock
    private EmployeeRepository repositoryMok;
    @InjectMocks
    private SalaryServiceImpl salaryServiceTest;

    @Test
    void salarySum() {
        when(repositoryMok.salarySum()).thenReturn(10);
        assertEquals(10, salaryServiceTest.salarySum());
    }

    @Test
    void salaryAvg() {
        when(repositoryMok.salaryAvg()).thenReturn(10);
        assertEquals(10, salaryServiceTest.salaryAvg());
    }

    @Test
    void employeeMinSalary() {
        when(repositoryMok.employeeMinSalary()).thenReturn(employeeTest);
        assertEquals(employeeDTOTest, salaryServiceTest.employeeMinSalary());
    }

    @Test
    void withHighestSalary() {
        when(repositoryMok.withHighestSalary()).thenReturn(employeeListTest);
        assertEquals(employeeDTOListTest, salaryServiceTest.withHighestSalary());
    }

    @Test
    void findBySalaryGreaterThan() {
        when(repositoryMok.findBySalaryGreaterThan(10)).thenReturn(employeeListTest);
        assertEquals(employeeDTOListTest, salaryServiceTest.findBySalaryGreaterThan(10));
    }
}