package io.mimsoft.controller;

import io.mimsoft.dto.ApiResponseDTO;
import io.mimsoft.dto.AuthResponseDTO;
import io.mimsoft.dto.LoginRequestDTO;
import io.mimsoft.dto.RegisterDTO;
import io.mimsoft.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/user")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        ApiResponseDTO apiResponse = authService.registerUser(registerDTO);
        if(apiResponse.isSuccess()){
            return ResponseEntity.ok(apiResponse);
        }
        else return ResponseEntity.status(409).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(authService.logout(httpServletRequest));
    }

}

