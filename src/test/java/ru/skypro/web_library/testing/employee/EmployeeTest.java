package ru.skypro.web_library.testing.employee;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.web_library.testing.dto.EmployeeDTO;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
public class EmployeeTest {
    @Autowired
    MockMvc mockMvc;
    JSONObject jsonObject;


    @BeforeEach
    public void setData() throws JSONException {
        jsonObject = new JSONObject();
        jsonObject.put("name", "Тест9");
        jsonObject.put("salary", "9");
        jsonObject.put("id", "9");
    }


    @Test
    @Transactional
    void addUsersToDatabases() throws Exception {
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(9))
                .andExpect(jsonPath("$[8].name").value("Тест9"));
    }

    @Test
    void getUsersFroTheDatabase() throws Exception {
        mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(8));
    }

    @Test
    @Transactional
    void changingUserDataToDatabases_correctId() throws Exception {
        jsonObject.put("id", "1");
        mockMvc.perform(put("/employees").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Тест9"));
    }

    @Test
    void changingUserDataToDatabases_notId() throws Exception {
        mockMvc.perform(put("/employees").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getEmployeeToId_correctId() throws Exception {
        mockMvc.perform(get("/employees/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Тест1"));
    }

    @Test
    void getEmployeeToId_notId() throws Exception {
        mockMvc.perform(get("/employees/9"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllEmployeeFullInfo() throws Exception {
        mockMvc.perform(get("/employees/full_info"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(8))
                .andExpect(jsonPath("$[0].positionName").value("бекенд разработчик"))
                .andExpect(jsonPath("$[0].salary").value("1"))
                .andExpect(jsonPath("$[0].name").value("Тест1"))
                .andExpect(jsonPath("$[7].salary").value("8"))
                .andExpect(jsonPath("$[7].name").value("Тест8"))
                .andExpect(jsonPath("$[7].positionName").value("руководитель проекта"));

    }

    @Test
    void getAllEmployeeToIdFullInfo_correctID() throws Exception {
        mockMvc.perform(get("/employees/full_info/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.positionName").value("бекенд разработчик"))
                .andExpect(jsonPath("$.salary").value("1"))
                .andExpect(jsonPath("$.name").value("Тест1"));
    }

    @Test
    void getAllEmployeeToIdFullInfo_notID() throws Exception {
        mockMvc.perform(get("/employees/full_info/10"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getEmployeeByPositionName_correctPosition() throws Exception {
        mockMvc.perform(get("/employees/position/бекенд разработчик"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].salary").value("1"))
                .andExpect(jsonPath("$[0].name").value("Тест1"))
                .andExpect(jsonPath("$[1].salary").value("2"))
                .andExpect(jsonPath("$[1].name").value("Тест2"));
    }

    @Test
    void getEmployeeByPositionName_positionIsBlank() throws Exception {
        mockMvc.perform(get("/employees/position/ "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(8));
    }

    @Test
    void getEmployeeFromPage_pageEmpty() throws Exception {
        mockMvc.perform(get("/employees/page/1 "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getEmployeeFromPage_pageNotEmpty() throws Exception {
        mockMvc.perform(get("/employees/page/0 "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(8));
    }

    @Test
    @Transactional
    void deleteEmployeeToId_correctId() throws Exception {
        mockMvc.perform(delete("/employees/1"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(7))
                .andExpect(jsonPath("$[0].salary").value("2"))
                .andExpect(jsonPath("$[0].name").value("Тест2"));
    }

    @Test
    void deleteEmployeeToId_notId() throws Exception {
        mockMvc.perform(delete("/employees/9"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void uploadAndSaveEmployees() throws Exception {
        List<EmployeeDTO> list = List.of(new EmployeeDTO(10, "10", 10),
                new EmployeeDTO(11, "11", 11),
                new EmployeeDTO(12, "12", 12));
        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.writeValueAsString(list);
        MockMultipartFile file = new MockMultipartFile("file", str.getBytes());
        mockMvc.perform(multipart("/employees/upload").file(file))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(11));
    }
}
