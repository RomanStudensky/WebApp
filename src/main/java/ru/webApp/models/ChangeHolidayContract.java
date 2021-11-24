package ru.webApp.models;

import lombok.Data;

@Data
public class ChangeHolidayContract extends  Contract {
    int id;
    String newBeginHoliday;
    String newEndHoliday;

    public ChangeHolidayContract() {
        newEndHoliday = new String();
        newBeginHoliday = new String();
    }

    public ChangeHolidayContract(int id, int idHol, String dateBegin, String dateEnd, boolean resolution,
                                 String newBeginHoliday, String newEndHoliday) {
        super(id, dateBegin, dateEnd, resolution);
        this.id = idHol;
        this.newBeginHoliday = newBeginHoliday;
        this.newEndHoliday = newEndHoliday;
    }
}
