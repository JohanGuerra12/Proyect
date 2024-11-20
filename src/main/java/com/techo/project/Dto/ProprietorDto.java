package com.techo.project.Dto;

import com.techo.project.Entitys.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProprietorDto {
    private Long id;
    private Users fk_id_user;
}
