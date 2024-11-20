package com.techo.project.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private Long document;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String role;
}