package ru.skypro.web_library.testing.service.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.web_library.testing.dto.ReportDTO;
import ru.skypro.web_library.testing.entity.ReportFile;
import ru.skypro.web_library.testing.exceptions.ExceptionNoId;
import ru.skypro.web_library.testing.repository.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


@Service
public class ReportServiceImpl implements ReportService {
    private final EmployeeRepository employeeRepository;
    private final ReportRepository reportRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    public ReportServiceImpl(EmployeeRepository employeeRepository, ReportRepository reportRepository) {
        this.employeeRepository = employeeRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public int downloadFile() throws IOException {
        LOGGER.info("Вызываем метод для вывода отчета, записи его в файл и сохранение данных файла в б/д");
        int id = (int) reportRepository.count();
        String fileName = newName(id);
        ObjectMapper objectMapper = new ObjectMapper();
        List<ReportDTO> report = employeeRepository.getReport();
        LOGGER.debug("Получаем данные из б/д сотрудников");
        // Создаем JSON-строку
        String json = objectMapper.writeValueAsString(report);
        // сщздаем и записываем файл
        File file = new File("src/report/" + fileName);
        Files.writeString(file.toPath(), json);
        ReportFile reportFile = new ReportFile(id + 1, file.getPath());
        reportRepository.save(reportFile);
        LOGGER.debug("Сохраняем данные о файле в б/д с отчетами");
        return reportFile.getId();
    }

    @Override
    public String getFile(int id) throws IOException, ExceptionNoId {
        LOGGER.info("Вызываем метод находит и возвращает созданный ранее файл в формате JSON по переданному уникальному идентификатору");
        ReportFile reportFile = reportRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Ошибка ExceptionNoId нет файла под id={}", id);
            return new ExceptionNoId();
        });
        return reportFile.getPath();
    }

    //    метод для создания нового имени файла
    private String newName(int id) {
        String newName = "report" + id + ".json";
        LOGGER.debug("Создаем новое имя файла {}", newName);
        return newName;
    }
}
