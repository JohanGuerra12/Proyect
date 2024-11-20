package com.techo.project.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techo.project.Entitys.Proprietor;
import com.techo.project.Entitys.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartamentDto {
    private Long id;

    @JsonProperty("apartament_number")
    private String apartament_number;

    @JsonProperty("tower_number")
    private String tower_number;

    @JsonProperty("fk_id_proprietor")
    private Proprietor fk_id_proprietor;

    @JsonProperty("fk_id_resident")
    private Users fk_id_resident;
}
