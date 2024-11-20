package com.techo.project.Controller;

import com.techo.project.Business.UsersBusiness;
import com.techo.project.Dto.UserDto;
import com.techo.project.Util.Exception.CustomException;
import com.techo.project.Util.Http.ResponseHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UsersController {

    @Autowired
    private UsersBusiness usersBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<UserDto> users = usersBusiness.findAll(page, size).getContent();
            List<Map<String, Object>> data = users.stream()
                    .map(this::convertUserDtoToMap)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Users retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        try {
            UserDto userDto = usersBusiness.findById(id);
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("User retrieved successfully", convertUserDtoToMap(userDto), HttpStatus.OK));
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDto userDto) {
        try {
            boolean userCreated = usersBusiness.createUser(userDto);
            if (userCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("User created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("User creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            userDto.setId(id);
            boolean userUpdated = usersBusiness.updateUser(userDto);
            if (userUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("User updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("User update failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdateError"));
            }
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        try {
            boolean userDeleted = usersBusiness.deleteUserById(id);
            if (userDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("User deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("User deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletionError"));
            }
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    private Map<String, Object> convertUserDtoToMap(UserDto userDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", userDto.getId());
        map.put("document", userDto.getDocument());
        map.put("name", userDto.getName());
        map.put("lastname", userDto.getLastname());
        map.put("email", userDto.getEmail());
        map.put("phone", userDto.getPhone());
        map.put("role", userDto.getRole());
        return map;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }
}
