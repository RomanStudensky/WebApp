package ru.webApp.models;

import lombok.Data;

@Data
public class AcademicSubject {
    int id;
    String name;

    public AcademicSubject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AcademicSubject() {

    }
}
