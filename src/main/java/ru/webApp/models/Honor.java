package ru.webApp.models;

import lombok.Data;

@Data
public class Honor {
    int id;
    String name;

    public Honor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Honor() {

    }
}
