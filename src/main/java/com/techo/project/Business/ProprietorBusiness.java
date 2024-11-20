package com.techo.project.Business;

import com.techo.project.Dto.ProprietorDto;
import com.techo.project.Entitys.Proprietor;
import com.techo.project.Entitys.Users;
import com.techo.project.Service.ProprietorService;
import com.techo.project.Service.UsersService;  // Asegúrate de tener este servicio para manejar los usuarios.
import com.techo.project.Util.Exception.CustomException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ProprietorBusiness {
    @Autowired
    private ProprietorService proprietorService;

    @Autowired
    private UsersService usersService;  // Asegúrate de inyectar el servicio de usuarios

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(ProprietorBusiness.class);

    // Método para obtener todos los propietarios con paginación
    public Page<ProprietorDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Proprietor> proprietorsPage = proprietorService.findAll(pageRequest);

            if (proprietorsPage.isEmpty()) {
                logger.info("No properties found!");
            }

            return proprietorsPage.map(proprietor -> modelMapper.map(proprietor, ProprietorDto.class));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting proprietors", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para obtener un propietario por su ID
    public ProprietorDto findById(Long id) {
        try {
            Proprietor proprietor = proprietorService.getById(id);
            if (proprietor != null) {
                return modelMapper.map(proprietor, ProprietorDto.class);
            } else {
                throw new CustomException("Not found", "Proprietor not found with that ID", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting proprietor by ID", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para crear un nuevo propietario
    public boolean createProprietor(ProprietorDto proprietorDto) {
        try {
            // Verificar si se pasó un usuario en fk_id_user
            if (proprietorDto.getFk_id_user() != null) {
                Users user = proprietorDto.getFk_id_user();  // Aquí ya tienes el objeto `User` completo
                Proprietor proprietor = modelMapper.map(proprietorDto, Proprietor.class);
                proprietor.setFk_id_user(user);  // Asignamos el usuario al propietario

                proprietorService.save(proprietor);
                logger.info("Proprietor created successfully");
                return true;
            } else {
                throw new CustomException("Bad Request", "User is required to create a proprietor", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating proprietor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para actualizar un propietario
    public boolean updateProprietor(ProprietorDto proprietorDto) {
        try {
            Proprietor existingProprietor = proprietorService.getById(proprietorDto.getId());
            if (existingProprietor != null) {
                // Si se pasó un usuario en fk_id_user, lo asignamos
                if (proprietorDto.getFk_id_user() != null) {
                    Users user = proprietorDto.getFk_id_user();  // Aquí ya tienes el objeto `User` completo
                    existingProprietor.setFk_id_user(user);  // Asignamos el usuario al propietario
                }

                // Mapear el resto de los campos del DTO al propietario existente
                modelMapper.map(proprietorDto, existingProprietor);

                proprietorService.save(existingProprietor);
                logger.info("Proprietor updated successfully");
                return true;
            } else {
                throw new CustomException("Not Found", "Proprietor not found with that ID", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error updating proprietor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para eliminar un propietario por su ID
    public boolean deleteProprietorById(Long id) {
        try {
            Proprietor proprietor = proprietorService.getById(id);
            if (proprietor != null) {
                proprietorService.deleteById(id);
                logger.info("Proprietor deleted successfully");
                return true;
            } else {
                throw new CustomException("Not Found", "Proprietor not found with that ID", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error deleting proprietor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
