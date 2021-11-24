package ru.webApp.models;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.ArrayList;

@Data
public class Employee {

    private int id;

    @NotEmpty(message = "Поле должно быть заполнена ")
    @Size(min = 2, max = 30, message = "Фамилия должна содержать до 30 символов")
    private String surname;

    @NotEmpty(message = "Поле должно быть заполнена ")
    @Size(min = 2, max = 30, message = "Name should be between 2 an 30 characters")
    private String name;

    @NotEmpty(message = "Поле должно быть заполнена ")
    @Size(min = 0, max = 30, message = "Name should be between 2 an 30 characters")
    private String fathername;

    @NotEmpty(message = "Поле должно быть заполнена ")
    private String birthd;

    @NotEmpty(message = "Поле должно быть заполнена")
    @Size(min = 6, max = 50, message = "Поле должно содержать от 6 до 50 символов")
    private String addres;

    @NotEmpty(message = "Поле должно быть заполнена ")
    @Max(value = 30, message = "Название категории должно содержать не более 30 символов")
    private String catheg;

    @NotEmpty(message = "Поле должно быть заполнена ")
    @Size(min = 12, max = 12, message = "ИНН должен содержать 12 цифр")
    private String inn;

    String experience;

    ArrayList<AcademicSubject> academicSubjects;

    ArrayList<Honor> honors;

    WorkContract workContract;

    DivorceContract divorceContract;

    WorkRecordCard workRecordCard;

    public Employee() {
    }

    public Employee(
            int id, String surname, String name,
            String fathername, String birdhd,
            String addres, String catheg, String inn,
            ArrayList<AcademicSubject> academicSubjects,
            ArrayList<Honor> honors,
            WorkContract workContract,
            DivorceContract divorceContract,
            WorkRecordCard workRecordCard) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.fathername = fathername;
        this.birthd = birdhd;
        this.addres = addres;
        this.catheg = catheg;
        this.inn = inn;
        this.academicSubjects = academicSubjects;
        this.honors = honors;
        this.workContract = workContract;
        this.divorceContract = divorceContract;
        this.workRecordCard = workRecordCard;
        this.experience = "";
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", fathername='" + fathername + '\'' +
                ", birthd='" + birthd + '\'' +
                ", addres='" + addres + '\'' +
                ", catheg='" + catheg + '\'' +
                ", inn='" + inn + '\'' +
                ", academicSubjects=" + academicSubjects +
                ", honors=" + honors +
                ", workContract=" + workContract +
                ", divorceContract=" + divorceContract +
                '}';
    }
}