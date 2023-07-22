package ru.skypro.web_library.testing.service.report;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import ru.skypro.web_library.testing.exceptions.ExceptionNoId;

import java.io.IOException;

public interface ReportService {
    int downloadFile() throws IOException;
    ResponseEntity<Resource> getFile(int id) throws IOException, ExceptionNoId;
}
