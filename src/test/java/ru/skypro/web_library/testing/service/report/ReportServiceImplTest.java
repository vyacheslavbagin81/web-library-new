package ru.skypro.web_library.testing.service.report;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.web_library.testing.exceptions.ExceptionNoId;
import ru.skypro.web_library.testing.repository.EmployeeRepository;
import ru.skypro.web_library.testing.repository.ReportRepository;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.skypro.web_library.testing.test_data.TestData.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepositoryMok;
    @Mock
    private ReportRepository reportRepositoryMok;
    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void downloadFileTest() throws IOException{
        when(employeeRepositoryMok.getReport()).thenReturn(reportDTOListTest);
        when(reportRepositoryMok.count()).thenReturn(1L);
        assertEquals(reportFileTest.getId(), reportService.downloadFile());
        verify(reportRepositoryMok, times(1)).save(reportFileTest);
    }
    @Test
    void getFile_correctId() throws IOException, ExceptionNoId{
        when(reportRepositoryMok.findById(1)).thenReturn(Optional.of(reportFileTest));
        assertEquals(reportFileTest.getPath(), reportService.getFile(1));
    }

    @Test
    void getFile_notId() throws IOException, ExceptionNoId{
        when(reportRepositoryMok.findById(1)).thenThrow(ExceptionNoId.class);
        assertThrows(ExceptionNoId.class, () -> reportService.getFile(1));
    }
}