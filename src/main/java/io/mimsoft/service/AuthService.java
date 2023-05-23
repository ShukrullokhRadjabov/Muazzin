package io.mimsoft.service;

import io.mimsoft.dto.*;
import io.mimsoft.entity.ProfileEntity;
import io.mimsoft.enums.UserRole;
import io.mimsoft.exceptions.AppBadRequestException;
import io.mimsoft.repository.ProfileRepository;
import io.mimsoft.util.JwtUtil;
import io.mimsoft.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponseDTO registerUser(RegisterDTO registerDTO) {
        if (profileRepository.existsByPhone(registerDTO.getPhone())) {
            return new ApiResponseDTO("This user already exists", false);
        }
        ProfileEntity profileEntity = new ProfileEntity(
                registerDTO.getName(),
                registerDTO.getSurname(),
                registerDTO.getPhone(),
                registerDTO.getEmail(),
                passwordEncoder.encode(registerDTO.getPassword()),
                UserRole.USER
        );
        profileRepository.save(profileEntity);
        return new ApiResponseDTO("Muvaffaqiyatli saqlandi", true);
    }

    public AuthResponseDTO login(LoginRequestDTO dto){
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndPassword(dto.getPhone(),  passwordEncoder.encode(dto.getPassword()));
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Phone or password incorrect");
        }
        ProfileEntity entity = optional.get();
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setName(entity.getName());
        authResponseDTO.setSurname(entity.getSurname());
        authResponseDTO.setJwt(JwtUtil.encode(entity.getPhone(), entity.getRole()));
        authResponseDTO.setRole(entity.getRole());
        return authResponseDTO;
    }
}
