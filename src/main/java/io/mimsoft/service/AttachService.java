package io.mimsoft.service;

import io.mimsoft.dto.AttachDTO;
import io.mimsoft.dto.AudioResponse;
import io.mimsoft.entity.AttachEntity;
import io.mimsoft.exceptions.AppBadRequestException;
import io.mimsoft.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class AttachService {
    @Value("${server.host}")
    private String serverHost;
    @Autowired
    private AttachRepository attachRepository;

    public AttachDTO upload(MultipartFile file) {
        try {
            String pathFolder = getYMDString();
            File folder = new File("attaches/" + pathFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            byte[] bytes = file.getBytes();
            String extension = getExtension(file.getOriginalFilename());

            AttachEntity attachEntity = new AttachEntity();
            attachEntity.setCreatedDate(LocalDateTime.now());
            attachEntity.setExtension(extension);
            attachEntity.setSize(file.getSize());
            attachEntity.setPath(pathFolder);
            attachEntity.setOriginalName(file.getOriginalFilename());
            attachRepository.save(attachEntity);
            Path path = Paths.get("attaches/" + pathFolder + "/" + attachEntity.getId() + "." + extension);
            Files.write(path, bytes);

            AttachDTO dto = new AttachDTO();
            dto.setId(attachEntity.getId());
            dto.setOriginalName(file.getOriginalFilename());
            dto.setUrl(serverHost + "/api/v1/attach/open/" + attachEntity.getId() + "." + extension);

            return dto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getYMDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public AttachDTO uploadAudio(MultipartFile file) {
        try {
            File folder = new File("audios/asma-ul-husna");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            byte[] bytes = file.getBytes();
            String extension = getExtension(file.getOriginalFilename());
            AttachEntity attachEntity = new AttachEntity();
            attachEntity.setCreatedDate(LocalDateTime.now());
            attachEntity.setExtension(extension);
            attachEntity.setSize(file.getSize());
            attachEntity.setOriginalName(file.getOriginalFilename());
            attachEntity.setPath(serverHost+"/audios/asma-ul-husna" +"/"+ attachEntity.getOriginalName());
            attachRepository.save(attachEntity);
            Path path = Paths.get("audios/asma-ul-husna" +"/"+ attachEntity.getOriginalName());
            Files.write(path, bytes);

            AttachDTO dto = new AttachDTO();
            dto.setId(attachEntity.getId());
            dto.setOriginalName(file.getOriginalFilename());
            dto.setUrl(serverHost +"/"+ path);
            return dto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AudioResponse getAudio() {
        Iterable<AttachEntity> all = attachRepository.findAll();
        List<AttachEntity> attach = new LinkedList<>();
        all.forEach(attachEntity -> {
            AttachEntity entity = new AttachEntity();
            entity.setOriginalName(attachEntity.getOriginalName());
            entity.setPath(attachEntity.getPath());
            entity.setSize(attachEntity.getSize());
            entity.setExtension(attachEntity.getExtension());
            entity.setCreatedDate(attachEntity.getCreatedDate());
            attach.add(entity);
        });
        if(attach==null){
            throw new AppBadRequestException("Attach not found");
        }
        for (AttachEntity attachEntity : attach) {
            if(attachEntity.getPath().contains("asma-ul-husna")){
                return new AudioResponse(attachEntity.getPath());
            }
        }
        return null;
    }
}