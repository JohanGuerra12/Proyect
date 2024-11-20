package com.techo.project.Business;

import com.techo.project.Service.UsersService;
import com.techo.project.Util.Exception.CustomException;
import com.techo.project.Dto.UserDto;
import com.techo.project.Entitys.Users;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UsersBusiness {

    @Autowired
    private UsersService usersService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(UsersBusiness.class);

    // Método para obtener todos los usuarios con paginación
    public Page<UserDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Users> usersPage = usersService.findAll(pageRequest);

            if (usersPage.isEmpty()) {
                logger.info("No users found!");
            }

            return usersPage.map(user -> modelMapper.map(user, UserDto.class));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para buscar un usuario por su ID
    public UserDto findById(Long id) {
        try {
            Users user = usersService.getById(id);
            if (user != null) {
                return modelMapper.map(user, UserDto.class);
            } else {
                throw new CustomException("Not Found", "User not found with that ID", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting user by ID", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para crear un nuevo usuario
    public boolean createUser(UserDto userDto) {
        try {
            Users user = modelMapper.map(userDto, Users.class);
            usersService.save(user);
            logger.info("User created successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para actualizar un usuario existente
    public boolean updateUser(UserDto userDto) {
        try {
            Users existingUser = usersService.getById(userDto.getId());
            if (existingUser != null) {
                Users updatedUser = modelMapper.map(userDto, Users.class);
                usersService.save(updatedUser);
                logger.info("User updated successfully");
                return true;
            } else {
                throw new CustomException("Not Found", "User not found with that ID", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error updating user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para eliminar un usuario por su ID
    public boolean deleteUserById(Long id) {
        try {
            Users user = usersService.getById(id);
            if (user != null) {
                usersService.deleteById(id);
                logger.info("User deleted successfully");
                return true;
            } else {
                throw new CustomException("Not Found", "User not found with that ID", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error deleting user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
