package com.techo.project.Business;

import com.techo.project.Dto.ApartamentDto;
import com.techo.project.Entitys.Apartament;
import com.techo.project.Entitys.Proprietor;
import com.techo.project.Entitys.Users;
import com.techo.project.Service.ApartamentService;
import com.techo.project.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApartamentBusiness {
    @Autowired
    private ApartamentService apartamentService;
    @Autowired
    UsersService usersService;


    public List<ApartamentDto> getAllApartaments() {
        return apartamentService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ApartamentDto getApartamentById(Long id) {
        Apartament apartament = apartamentService.getById(id);
        return convertToDto(apartament);
    }

    public void saveApartament(ApartamentDto apartamentDto) {
        Apartament apartament = convertToEntity(apartamentDto);
        apartamentService.save(apartament);
    }

    public void deleteApartamentById(Long id) {
        apartamentService.deleteById(id);
    }

    private ApartamentDto convertToDto(Apartament apartament) {
        return new ApartamentDto(
                apartament.getId(),
                apartament.getApartament_number(), // Usa el atributo con guion bajo
                apartament.getTower_number(),     // Usa el atributo con guion bajo
                apartament.getFk_id_proprietor(), // Usa el atributo con guion bajo
                apartament.getFk_id_resident()    // Usa el atributo con guion bajo
        );
    }

    private Apartament convertToEntity(ApartamentDto apartamentDto) {
        System.out.println("DTO recibido: " + apartamentDto);

        Apartament apartament = new Apartament();
        apartament.setId(apartamentDto.getId());
        apartament.setApartament_number(apartamentDto.getApartament_number());
        apartament.setTower_number(apartamentDto.getTower_number());

        if (apartamentDto.getFk_id_proprietor() != null) {
            Proprietor proprietor = new Proprietor();
            proprietor.setId(apartamentDto.getFk_id_proprietor().getId());
            apartament.setFk_id_proprietor(proprietor);
        }

        if (apartamentDto.getFk_id_resident() != null) {
            Users resident = new Users();
            resident.setId(apartamentDto.getFk_id_resident().getId());
            // Aquí necesitas cargar el usuario completo desde la base de datos
            // Por ejemplo, si tienes un `UsersService`, puedes buscarlo por su ID
            Users fullResident = usersService.getById(apartamentDto.getFk_id_resident().getId());
            apartament.setFk_id_resident(fullResident);
        }

        System.out.println("Entidad convertida: " + apartament);
        return apartament;
    }


}
