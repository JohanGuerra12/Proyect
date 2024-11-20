package com.techo.project.Entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "apartament")
public class Apartament {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private  Long id;

    @Column(name = "apartament_number")
    private String apartament_number;

    @Column(name = "tower_number")
    private String tower_number;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_proprietor")
    private Proprietor fk_id_proprietor;


    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_resident")
    private Users fk_id_resident;
}
