package ru.skypro.web_library.testing.salary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
public class SalaryTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void salarySum() throws Exception {
        mockMvc.perform(get("/salary/sum"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(36));
    }

    @Test
    void salaryAvg() throws Exception {
        mockMvc.perform(get("/salary/avg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(4));
    }

    @Test
    void employeeMinSalary() throws Exception {
        mockMvc.perform(get("/salary/min"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salary").value(1))
                .andExpect(jsonPath("$.name").value("Тест1"));
    }

    @Test
    void withHighestSalary() throws Exception {
        mockMvc.perform(get("/salary/withHighestSalary"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].salary").value(8))
                .andExpect(jsonPath("$[0].name").value("Тест8"));
    }

    @Test
    void findBySalaryGreaterThan() throws Exception {
        mockMvc.perform(get("/salary/salaryHigherThan/?salary=5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].salary").value(6))
                .andExpect(jsonPath("$[0].name").value("Тест6"));
    }
}
