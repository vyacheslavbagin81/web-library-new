package ru.skypro.web_library.testing.report;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReportTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void downloadFile() throws Exception {
        mockMvc.perform(get("/report/download"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("1"));
    }

    @Test
    void getReportToId_correctId() throws Exception {
        mockMvc.perform(get("/report/download"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("1"));

        mockMvc.perform(get("/report/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5));
    }
}
