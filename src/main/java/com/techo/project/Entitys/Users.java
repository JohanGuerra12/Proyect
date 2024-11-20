package com.techo.project.Entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "document" , nullable = false)
    private Long document;

    @Column(name="name", nullable = false, length = 45)
    private String name;

    @Column(name="lastname", nullable = false, length = 45)
    private String lastname;


    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "role", nullable = false, length = 45)
    private String role;
}
