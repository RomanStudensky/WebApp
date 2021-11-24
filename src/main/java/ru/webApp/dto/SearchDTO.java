package ru.webApp.dto;

import lombok.Data;

@Data
public class SearchDTO {
    String search;

    public SearchDTO() {
        search = new String();
    }
}
