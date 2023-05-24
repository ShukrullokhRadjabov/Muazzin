package io.mimsoft.controller;

import io.mimsoft.dto.AttachDTO;
import io.mimsoft.dto.AudioResponse;
import io.mimsoft.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/photo/admin/upload")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttachDTO> uploadPhoto(@RequestParam("file") MultipartFile file) {
        AttachDTO response = attachService.upload(file);
        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/audio/admin/upload")
    public ResponseEntity<AttachDTO> uploadAudio(@RequestParam("file") MultipartFile file) {
        AttachDTO res = attachService.uploadAudio(file);
        return ResponseEntity.ok().body(res);
    }


    @GetMapping("/audio/user/get")
    public ResponseEntity<AudioResponse> getAudio(){
        AudioResponse response = attachService.getAudio();
        return ResponseEntity.ok().body(response);
    }
}
