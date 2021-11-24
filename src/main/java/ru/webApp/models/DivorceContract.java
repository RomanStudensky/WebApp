package ru.webApp.models;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class DivorceContract extends Contract {
    int id;
    @Size(min = 3, max = 50)
    String terminateEvent;

    public DivorceContract() { }

    public DivorceContract(int id, int idDivContr, String dateBegin, String dateEnd, boolean resolution, String terminateEvent) {
        super(id, dateBegin, dateEnd, resolution);
        this.id = idDivContr;
        this.terminateEvent = terminateEvent;
    }
}
