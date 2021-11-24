package ru.webApp.models;

import lombok.Data;
import java.util.ArrayList;

@Data
public class WorkRecordCard {

    int id;
    int number;
    String date;
    Employee employee;
    ArrayList<WorkRecord> employmentRecords;

    public WorkRecordCard(int id, int number, String date, Employee employee){
        this.id = id;
        this.number = number;
        this.date = date;
        this.employee = employee;
        this.employmentRecords = employmentRecords;
    }

    public WorkRecordCard() {

    }
}
