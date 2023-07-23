package ru.skypro.web_library.testing.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.web_library.testing.exceptions.ExceptionNoId;
import ru.skypro.web_library.testing.service.report.ReportService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/report/")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /*POST-запрос localhost:8080/report
    Он должен формировать JSON-файл со статистикой по отделам:
        Название отдела.
        Количество сотрудников.
        Максимальная зарплата.
        Минимальная зарплата.
        Средняя зарплата.
        А также сохранять содержимое файла в базу данных. Метод возвращает целочисленный идентификатор сохраненного в базе данных файла.*/
    @GetMapping("download")
    int downloadFile() throws IOException {
        return reportService.downloadFile();
    }

    /*GET-запрос localhost:8080/report/{id}
    Он должен находить и возвращать созданный ранее файл в формате JSON по переданному уникальному идентификатору.*/
    @GetMapping("{id}")
    ResponseEntity<Resource> getFile(@PathVariable int id) throws IOException, ExceptionNoId {
        String fileName = reportService.getFile(id);
        Resource resource = new ByteArrayResource(Files.readAllBytes(Path.of(fileName)));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);
    }

}
