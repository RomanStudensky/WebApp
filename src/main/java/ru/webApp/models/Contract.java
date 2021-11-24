package ru.webApp.models;

import lombok.Data;

@Data
public class Contract {
    int id;
    String dateBegin;
    String dateEnd;
    boolean resolution;

    public Contract() {}

    public Contract(int id, String dateBegin, String dateEnd, boolean resolution) {
        this.id = id;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.resolution = resolution;
    }
}
