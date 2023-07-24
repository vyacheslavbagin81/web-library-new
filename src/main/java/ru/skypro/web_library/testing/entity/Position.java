package ru.skypro.web_library.testing.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name_position")
    private String namePosition;

    public Position() {
    }

    public Position(int id, String namePosition) {
        this.id = id;
        this.namePosition = namePosition;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNamePosition() {
        return namePosition;
    }

    public void setNamePosition(String name) {
        this.namePosition = name;
    }

}
