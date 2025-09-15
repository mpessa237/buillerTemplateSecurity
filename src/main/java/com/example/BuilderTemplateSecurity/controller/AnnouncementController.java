package com.example.BuilderTemplateSecurity.controller;

import com.example.BuilderTemplateSecurity.dto.AnnouncementReqDTO;
import com.example.BuilderTemplateSecurity.dto.AnnouncementRespDTO;
import com.example.BuilderTemplateSecurity.entity.Announcement;
import com.example.BuilderTemplateSecurity.service.AnnouncementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final ObjectMapper objectMapper;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AnnouncementRespDTO> createAnnouncement(@Validated @RequestBody AnnouncementReqDTO announcementReqDTO){
        AnnouncementRespDTO createdAnnouncement = announcementService.createAnnouncement(announcementReqDTO);
        return new ResponseEntity<>(createdAnnouncement,HttpStatus.CREATED);
    }
}
