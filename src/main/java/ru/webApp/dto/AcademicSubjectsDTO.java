package ru.webApp.dto;

import lombok.Data;
import java.util.List;

@Data
public class AcademicSubjectsDTO {

    private List<Integer> academ_subs_dto;

    public AcademicSubjectsDTO(List<Integer> academ_subs_dto) {
        this.academ_subs_dto = academ_subs_dto;
    }

}

