package ru.skypro.web_library.testing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.web_library.testing.entity.ReportFile;
@Repository
public interface ReportRepository extends JpaRepository<ReportFile,Integer> {

}

