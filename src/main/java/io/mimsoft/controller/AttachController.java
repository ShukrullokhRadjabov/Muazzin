package io.mimsoft.controller;

import io.mimsoft.dto.AttachDTO;
import io.mimsoft.service.AttachService;
import org.hibernate.proxy.AbstractSerializableProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/user/upload")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        AttachDTO response = attachService.upload(file);
        return ResponseEntity.ok().body(response);
    }
}
