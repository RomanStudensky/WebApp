package ru.webApp.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class NewEmployeeDTO {

    private int id;

    @NotEmpty(message = "Поле должно быть заполнено ")
    @Size(min = 2, max = 30, message = "Фамилия должна содержать до 30 символов")
    private String surname;

    @NotEmpty(message = "Поле должно быть заполнено ")
    @Size(min = 2, max = 30, message = "Name should be between 2 an 30 characters")
    private String name;

    @NotEmpty(message = "Поле должно быть заполнено ")
    @Size(min = 0, max = 30, message = "Name should be between 2 an 30 characters")
    private String fathername;

    @NotEmpty(message = "Поле должно быть заполнено ")
    private String birthd;

    @NotEmpty(message = "Поле должно быть заполнено")
    @Size(min = 6, max = 50, message = "Поле должно содержать от 6 до 50 символов")
    private String addres;

    @NotEmpty(message = "Поле должно быть заполнено ")
    @Size(min = 2, max = 30, message = "Название категории должно содержать не более 30 символов")
    private String catheg;

    @NotEmpty(message = "Поле должно быть заполнено ")
    @Size(min = 12, max = 12, message = "ИНН должен содержать 12 цифр")
    @Digits(integer = 12, fraction = 0)
    private String inn;

    @Digits(integer = 5, fraction = 2)
    private double wage;

    @NotEmpty(message = "Поле должно быть заполнено ")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    String holidayBegin;

    @NotEmpty(message = "Поле должно быть заполнено ")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    String holidayEnd;

    @NotEmpty(message = "Поле должно быть заполнено ")
    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    String post;

    @NotEmpty(message = "Поле должно быть заполнено ")
    @DateTimeFormat(pattern = "HH:mm")
    String workdayStart;

    @NotEmpty(message = "Поле должно быть заполнено ")
    @DateTimeFormat(pattern = "HH:mm")
    String workdayFinish;

    @NotEmpty(message = "Поле должно быть заполнено ")
    @Size(min = 1, max = 30)
    String cathedra;

    public NewEmployeeDTO(){

    }

}
