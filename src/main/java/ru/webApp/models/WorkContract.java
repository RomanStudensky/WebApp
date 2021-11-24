package ru.webApp.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class WorkContract extends Contract{
    int id;
    double wage;
    String holidayBegin;
    String holidayEnd;
    String post;
    String workdayStart;
    String workdayFinish;
    String cathedra;
    ArrayList<ChangeHolidayContract> changeHolidayContracts;
    ArrayList<ChangePostContract> changePostContracts;

    public WorkContract() {}

    public WorkContract(int id, int idWorkCo, String dateBegin, String dateEnd,
                        boolean resolution, double wage,
                        String holidayBegin, String holidayEnd,
                        String post, String workdayStart,
                        String workdayFinish, String cathedra) {
        super(id, dateBegin, dateEnd, resolution);
        this.id = idWorkCo;
        this.wage = wage;
        this.holidayBegin = holidayBegin;
        this.holidayEnd = holidayEnd;
        this.post = post;
        this.workdayStart = workdayStart;
        this.workdayFinish = workdayFinish;
        this.cathedra = cathedra;
        this.changeHolidayContracts = new ArrayList<>();
        this.changePostContracts = new ArrayList<>();
    }

    public void addChangeHolidayContract(ChangeHolidayContract changeHolidayContract) {
        this.changeHolidayContracts.add(changeHolidayContract);
    }

    public void addChangePostContract(ChangePostContract changePostContract) {
        this.changePostContracts.add(changePostContract);
    }
}
