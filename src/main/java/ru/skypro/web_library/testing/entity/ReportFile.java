package ru.skypro.web_library.testing.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "report_files")
public class ReportFile {
    @Id()
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String path;

    public ReportFile(int id, String path) {
        this.id = id;
        this.path = path;
    }

    public ReportFile() {

    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportFile that = (ReportFile) o;
        return id == that.id && path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path);
    }
}
