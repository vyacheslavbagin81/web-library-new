package ru.skypro.web_library.testing.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.web_library.testing.dto.ReportDTO;
import ru.skypro.web_library.testing.dto.EmployeeFullInfo;
import ru.skypro.web_library.testing.entity.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer>, PagingAndSortingRepository<Employee, Integer> {
    @Query("select new ru.skypro.web_library.testing.dto.EmployeeFullInfo(e.id, e.name, e.salary, p.namePosition) " +
            "from Employee e left join Position p where e.position.id = p.id")
    List<EmployeeFullInfo> getEmployeeFullInfo();

    @Query("select new ru.skypro.web_library.testing.dto.EmployeeFullInfo(e.id, e.name, e.salary, p.namePosition) " +
            "from Employee e left join Position p where e.position.id = p.id and e.id = ?1")
    Optional<EmployeeFullInfo> getAllEmployeeToIdFullInfo(int id);

    @Query("select e from Employee e where e.position.namePosition = ?1")
    List<Employee> findEmployeeByPosition_Name(String name);

    @Query("select sum(salary) from Employee ")
    Integer salarySum();

    @Query("select avg(salary) from Employee")
    Integer salaryAvg();

    @Query(value = "select * from employee " +
            "where salary = (SELECT MIN(salary) FROM employee) " +
            "limit 1",
            nativeQuery = true)
    Employee employeeMinSalary();

    @Query(value = "select e from Employee e where e.salary = (SELECT MAX(salary) FROM Employee)")
    List<Employee> withHighestSalary();

    List<Employee> findBySalaryGreaterThan(int salary);

    @Transactional
    @Query(value = "select new ru.skypro.web_library.testing.dto.ReportDTO(p.namePosition, count(e) , max(e.salary) , min(e.salary) , avg (e.salary)) " +
            "from Employee e left join e.position p " +
            "group by p.namePosition")
    List<ReportDTO> getReport();

}
