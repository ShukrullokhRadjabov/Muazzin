package io.mimsoft.service;

import io.mimsoft.dto.ApiResponseDTO;
import io.mimsoft.dto.AsmaUlHusnaRequestDTO;
import io.mimsoft.dto.AsmaUlHusnaResponseDTO;
import io.mimsoft.entity.AsmaUlHusnaEntity;
import io.mimsoft.entity.AttachEntity;
import io.mimsoft.exceptions.AppBadRequestException;
import io.mimsoft.repository.AsmaUlHusnaRepository;
import io.mimsoft.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AsmaUlHusnaService {
    @Autowired
    private AsmaUlHusnaRepository asmaUlHusnaRepository;
    @Autowired
    private AttachRepository attachRepository;

    public ApiResponseDTO create(AsmaUlHusnaRequestDTO dto, String lang) {
        switch (lang) {
            case "uz" -> {
                AsmaUlHusnaEntity entity = new AsmaUlHusnaEntity();
                entity.setArabicTitle(dto.getArabicTitle());
                entity.setTitleUzb(dto.getTitle());
                entity.setDescriptionUzb(dto.getDescription());
                entity.setInfoUzb(dto.getInfo());
                entity.setAttachId(dto.getAttachId());
                entity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                asmaUlHusnaRepository.save(entity);
            }
            case "ru" -> {
                AsmaUlHusnaEntity entity = new AsmaUlHusnaEntity();
                entity.setArabicTitle(dto.getArabicTitle());
                entity.setTitleRu(dto.getTitle());
                entity.setDescriptionRu(dto.getDescription());
                entity.setInfoRu(dto.getInfo());
                entity.setAttachId(dto.getAttachId());
                entity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                asmaUlHusnaRepository.save(entity);
            }
            case "eng" -> {
                AsmaUlHusnaEntity entity = new AsmaUlHusnaEntity();
                entity.setArabicTitle(dto.getArabicTitle());
                entity.setTitleEng(dto.getTitle());
                entity.setDescriptionEng(dto.getDescription());
                entity.setInfoEng(dto.getInfo());
                entity.setAttachId(dto.getAttachId());
                entity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                asmaUlHusnaRepository.save(entity);
            }
        }

        return new ApiResponseDTO("Successfully add", true);
    }

    public AsmaUlHusnaResponseDTO getAsmaUlHusna(String lang, Integer id) {
        Optional<AsmaUlHusnaEntity> optional = asmaUlHusnaRepository.findById(Long.valueOf(id));

        switch (lang) {
            case "uz" -> {
                if (!optional.isPresent()) {
                    throw new AppBadRequestException("Noto'g'ri id");
                }
                AsmaUlHusnaEntity entity = optional.get();
                Optional<AttachEntity> attach = attachRepository.findById(entity.getAttachId());

                AsmaUlHusnaResponseDTO dto = new AsmaUlHusnaResponseDTO();
                dto.setArabicTitle(entity.getArabicTitle());
                dto.setTitle(entity.getTitleUzb());
                dto.setInfo(entity.getInfoUzb());
                dto.setDescription(entity.getDescriptionUzb());
                dto.setAttach(attach.get());
                return dto;
            }
            case "ru" -> {
                if (!optional.isPresent()) {
                    throw new AppBadRequestException("Неверный ID");
                }
                AsmaUlHusnaEntity entity = optional.get();
                Optional<AttachEntity> attach = attachRepository.findById(entity.getAttachId());

                AsmaUlHusnaResponseDTO dto = new AsmaUlHusnaResponseDTO();
                dto.setArabicTitle(entity.getArabicTitle());
                dto.setTitle(entity.getTitleRu());
                dto.setInfo(entity.getInfoRu());
                dto.setDescription(entity.getDescriptionRu());
                dto.setAttach(attach.get());
                return dto;
            }
            case "eng" -> {
                if (!optional.isPresent()) {
                    throw new AppBadRequestException("Wrong id");
                }
                AsmaUlHusnaEntity entity = optional.get();
                Optional<AttachEntity> attach = attachRepository.findById(entity.getAttachId());

                AsmaUlHusnaResponseDTO dto = new AsmaUlHusnaResponseDTO();
                dto.setArabicTitle(entity.getArabicTitle());
                dto.setTitle(entity.getTitleEng());
                dto.setInfo(entity.getInfoEng());
                dto.setDescription(entity.getDescriptionEng());
                dto.setAttach(attach.get());
                return dto;
            }
        }
        throw new AppBadRequestException("Wrong language");
    }
}
