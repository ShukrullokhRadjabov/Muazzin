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
            case "uzb" -> {
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
        Optional<AsmaUlHusnaEntity> optional = asmaUlHusnaRepository.findById(id);

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
    public List<AsmaUlHusnaResponseDTO> getAsmaUlHusnaList(String lang) {
        Iterable<AsmaUlHusnaEntity> all = asmaUlHusnaRepository.findAll();
        List<AsmaUlHusnaResponseDTO> englist = new LinkedList<>();
        List<AsmaUlHusnaResponseDTO> ruList = new LinkedList<>();
        List<AsmaUlHusnaResponseDTO> uzbList = new LinkedList<>();
        all.forEach(asmaUlHusnaEntity -> {
            AsmaUlHusnaResponseDTO dtoEng = new AsmaUlHusnaResponseDTO();
            if (asmaUlHusnaEntity.getTitleEng() != null) {
                Optional<AttachEntity> attach = attachRepository.findById(asmaUlHusnaEntity.getAttachId());
                dtoEng.setTitle(asmaUlHusnaEntity.getTitleEng());
                dtoEng.setInfo(asmaUlHusnaEntity.getInfoEng());
                dtoEng.setDescription(asmaUlHusnaEntity.getDescriptionEng());
                dtoEng.setArabicTitle(asmaUlHusnaEntity.getArabicTitle());
                dtoEng.setAttach(attach.get());
                englist.add(dtoEng);
            }
            AsmaUlHusnaResponseDTO dtoRu = new AsmaUlHusnaResponseDTO();
            if (asmaUlHusnaEntity.getTitleRu() != null) {
                Optional<AttachEntity> attach = attachRepository.findById(asmaUlHusnaEntity.getAttachId());
                dtoRu.setTitle(asmaUlHusnaEntity.getTitleRu());
                dtoRu.setInfo(asmaUlHusnaEntity.getInfoRu());
                dtoRu.setDescription(asmaUlHusnaEntity.getDescriptionRu());
                dtoRu.setArabicTitle(asmaUlHusnaEntity.getArabicTitle());
                dtoRu.setAttach(attach.get());
                ruList.add(dtoRu);
            }

            AsmaUlHusnaResponseDTO dtoUzb = new AsmaUlHusnaResponseDTO();
            if (asmaUlHusnaEntity.getTitleUzb() != null) {
                Optional<AttachEntity> attach = attachRepository.findById(asmaUlHusnaEntity.getAttachId());
                dtoUzb.setTitle(asmaUlHusnaEntity.getTitleUzb());
                dtoUzb.setInfo(asmaUlHusnaEntity.getInfoUzb());
                dtoUzb.setDescription(asmaUlHusnaEntity.getDescriptionUzb());
                dtoUzb.setArabicTitle(asmaUlHusnaEntity.getArabicTitle());
                dtoUzb.setAttach(attach.get());
                uzbList.add(dtoUzb);
            }
        });
        switch (lang) {
            case "uzb" -> {
                return uzbList;
            }
            case "ru" -> {
                return ruList;
            }
            case "eng" -> {
                return englist;
            }
        }
        throw new AppBadRequestException("Language not found");
    }

    public ApiResponseDTO update(AsmaUlHusnaRequestDTO dto, String lang, Integer id) {
        Optional<AsmaUlHusnaEntity> optional = asmaUlHusnaRepository.findById(id);
        if (!optional.isPresent()) {
            throw new AppBadRequestException("Asma ul husna not found");
        }
        AsmaUlHusnaEntity entity = optional.get();
        switch (lang) {
            case "uzb" -> {
                entity.setTitleUzb(dto.getTitle());
                entity.setDescriptionUzb(dto.getDescription());
                entity.setInfoUzb(dto.getInfo());
                entity.setArabicTitle(dto.getArabicTitle());
                entity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                asmaUlHusnaRepository.save(entity);
                return new ApiResponseDTO("Muvaffaqiyatli saqlandi", true);
            }
            case "ru" -> {
                entity.setTitleRu(dto.getTitle());
                entity.setDescriptionRu(dto.getDescription());
                entity.setInfoRu(dto.getInfo());
                entity.setArabicTitle(dto.getArabicTitle());
                entity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                asmaUlHusnaRepository.save(entity);
                return new ApiResponseDTO("Успешно сохранено", true);
            }
            case "eng" -> {
                entity.setTitleEng(dto.getTitle());
                entity.setDescriptionEng(dto.getDescription());
                entity.setInfoEng(dto.getInfo());
                entity.setArabicTitle(dto.getArabicTitle());
                entity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                asmaUlHusnaRepository.save(entity);
                return new ApiResponseDTO("Successfully saved", true);
            }
            default -> new ApiResponseDTO("Language not found", false);
        }
        throw new AppBadRequestException("Bad request");
    }
}
