package io.mimsoft.service;

import io.mimsoft.dto.*;
import io.mimsoft.entity.ProfileEntity;
import io.mimsoft.enums.UserRole;
import io.mimsoft.exceptions.AppBadRequestException;
import io.mimsoft.repository.ProfileRepository;
import io.mimsoft.util.JwtUtil;
import io.mimsoft.util.MD5Util;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    public ApiResponseDTO registerUser(RegisterDTO registerDTO) {
        if (profileRepository.existsByPhone(registerDTO.getPhone())) {
            return new ApiResponseDTO("This user already exists", false);
        }
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(registerDTO.getName());
        profileEntity.setSurname(registerDTO.getSurname());
        profileEntity.setEmail(registerDTO.getEmail());
        profileEntity.setPassword(MD5Util.getMd5Hash(registerDTO.getPassword()));
        profileEntity.setPhone(registerDTO.getPhone());
        profileEntity.setRole(UserRole.ROLE_USER);
        profileEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        profileEntity.setSessionCode(UUID.randomUUID().toString());
        profileRepository.save(profileEntity);
        return new ApiResponseDTO("Muvaffaqiyatli saqlandi", true);
    }

    //e53a0a2978c28872a4505bdb51db06dc
    public AuthResponseDTO login(LoginRequestDTO dto){
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndPassword(dto.getPhone(), MD5Util.getMd5Hash(dto.getPassword()));
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Phone or password incorrect");
        }
        ProfileEntity entity = optional.get();
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setName(entity.getName());
        authResponseDTO.setSurname(entity.getSurname());
        authResponseDTO.setJwt(JwtUtil.encode(entity.getPhone(), entity.getRole(), entity.getSessionCode()));
        authResponseDTO.setRole(entity.getRole());
        return authResponseDTO;
    }

    public String logout(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        String token = header.substring(7);
        JwtDTO jwtDto = JwtUtil.decode(token);
        int result = profileRepository.logoutProfile("", jwtDto.getPhone());
        System.out.println(result);
        return "";
    }
}
