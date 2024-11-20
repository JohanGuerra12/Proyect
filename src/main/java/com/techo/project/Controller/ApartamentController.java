package com.techo.project.Controller;

import com.techo.project.Business.ApartamentBusiness;
import com.techo.project.Dto.ApartamentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartaments")
public class ApartamentController {
    @Autowired
    private ApartamentBusiness apartamentBusiness;

    @GetMapping("/all")
    public ResponseEntity<List<ApartamentDto>> getAllApartaments() {
        return ResponseEntity.ok(apartamentBusiness.getAllApartaments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartamentDto> getApartamentById(@PathVariable Long id) {
        return ResponseEntity.ok(apartamentBusiness.getApartamentById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveApartament(@RequestBody ApartamentDto apartamentDto) {
        System.out.println("JSON recibido en el controlador: " + apartamentDto);

        // Verificar si las propiedades específicas están nulas
        if (apartamentDto.getFk_id_proprietor() != null) {
            System.out.println("Proprietor ID: " + apartamentDto.getFk_id_proprietor().getId());
        } else {
            System.out.println("Proprietor es nulo");
        }

        if (apartamentDto.getFk_id_resident() != null) {
            System.out.println("Resident ID: " + apartamentDto.getFk_id_resident().getId());
        } else {
            System.out.println("Resident es nulo");
        }

        apartamentBusiness.saveApartament(apartamentDto);
        return ResponseEntity.ok("Apartament saved successfully.");
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApartamentById(@PathVariable Long id) {
        apartamentBusiness.deleteApartamentById(id);
        return ResponseEntity.ok("Apartament deleted successfully.");
    }
}
