package com.techo.project.Controller;

import com.techo.project.Business.ProprietorBusiness;
import com.techo.project.Dto.ProprietorDto;
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
@RequestMapping("/proprietor")
public class ProprietorController {

    @Autowired
    private ProprietorBusiness proprietorBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllProprietors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<ProprietorDto> proprietors = proprietorBusiness.findAll(page, size).getContent();
            List<Map<String, Object>> data = proprietors.stream()
                    .map(this::convertProprietorDtoToMap)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Proprietors retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProprietorById(@PathVariable Long id) {
        try {
            ProprietorDto proprietorDto = proprietorBusiness.findById(id);
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Proprietor retrieved successfully", convertProprietorDtoToMap(proprietorDto), HttpStatus.OK));
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createProprietor(@RequestBody ProprietorDto proprietorDto) {
        try {
            boolean proprietorCreated = proprietorBusiness.createProprietor(proprietorDto);
            if (proprietorCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Proprietor created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Proprietor creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateProprietor(@PathVariable Long id, @RequestBody ProprietorDto proprietorDto) {
        try {
            proprietorDto.setId(id);
            boolean proprietorUpdated = proprietorBusiness.updateProprietor(proprietorDto);
            if (proprietorUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Proprietor updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Proprietor update failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdateError"));
            }
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteProprietor(@PathVariable Long id) {
        try {
            boolean proprietorDeleted = proprietorBusiness.deleteProprietorById(id);
            if (proprietorDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Proprietor deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Proprietor deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletionError"));
            }
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    private Map<String, Object> convertProprietorDtoToMap(ProprietorDto proprietorDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", proprietorDto.getId());
        map.put("fk_id_user", proprietorDto.getFk_id_user() != null ? proprietorDto.getFk_id_user().getId() : null);
        return map;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }
}
