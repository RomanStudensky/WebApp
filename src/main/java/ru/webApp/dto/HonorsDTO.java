package ru.webApp.dto;

import lombok.Data;

import java.util.List;

@Data
public class HonorsDTO {

    private List<Integer> honors_dto;

    public HonorsDTO(List<Integer> honors_dto) {
        this.honors_dto = honors_dto;
    }

}

