package io.mimsoft.controller;

import io.mimsoft.dto.ApiResponseDTO;
import io.mimsoft.dto.AsmaUlHusnaRequestDTO;
import io.mimsoft.dto.AsmaUlHusnaResponseDTO;
import io.mimsoft.service.AsmaUlHusnaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/asma-ul-husna")
public class AsmaUlHusnaController {

    @Autowired
    private AsmaUlHusnaService asmaUlHusnaService;
    @PostMapping({"/user/create/{lang}"})
    public ResponseEntity<ApiResponseDTO> create(@RequestBody AsmaUlHusnaRequestDTO dto,
                                                 @PathVariable String lang) {
        return ResponseEntity.ok(asmaUlHusnaService.create(dto, lang));
    }

    @GetMapping(value = "/user/get")
    public ResponseEntity<?> getList(@PathVariable("lang")String lang, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(asmaUlHusnaService.getAsmaUlHusna(lang, id));
    }


    @GetMapping(value = "/user/get-list")
    public ResponseEntity<List<AsmaUlHusnaResponseDTO>> getList(@PathVariable("lang")String lang) {
        return ResponseEntity.ok(asmaUlHusnaService.getAsmaUlHusnaList(lang));
    }

}
