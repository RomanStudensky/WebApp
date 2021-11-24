package ru.webApp.models;

import lombok.Data;

@Data
public class WorkRecord {
    int id;
    String date;
    String title;

    public WorkRecord(int id, String date, String title) {
        this.id = id;
        this.date = date;
        this.title = title;
    }
}
