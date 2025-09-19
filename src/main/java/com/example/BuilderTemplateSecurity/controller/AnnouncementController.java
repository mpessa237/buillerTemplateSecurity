package com.example.BuilderTemplateSecurity.controller;

import com.example.BuilderTemplateSecurity.dto.AnnouncementReqDTO;
import com.example.BuilderTemplateSecurity.dto.AnnouncementRespDTO;
import com.example.BuilderTemplateSecurity.entity.Announcement;
import com.example.BuilderTemplateSecurity.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<AnnouncementRespDTO> createAnnouncement(
            @RequestPart("announcement")AnnouncementReqDTO announcementReqDTO,
            @RequestPart("files")List<MultipartFile> files)throws IOException {

        AnnouncementRespDTO createdDto = announcementService.createAnnouncement(announcementReqDTO, files);
        return new ResponseEntity<>(createdDto,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementRespDTO>> getAll(){
        List<AnnouncementRespDTO> dtoList = announcementService.getAll();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{announcementId}")
    public ResponseEntity<AnnouncementRespDTO> getAnnouncementById(@PathVariable("announcementId") Long announcementId){
        AnnouncementRespDTO dto = announcementService.getAnnouncementById(announcementId);
        return ResponseEntity.ok(dto);
    }


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/active")
    public ResponseEntity<List<Announcement>> getAllActiveAnnouncement(){
        List<Announcement> activeAnnouncements = announcementService.getAllActive();
        return new ResponseEntity<>(activeAnnouncements,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{announcementId}")
    public ResponseEntity<Announcement> update(@RequestBody Announcement announcement,@PathVariable Long announcementId){
        announcement.setAnnouncementId(announcementId);
        Announcement announcement1 = announcementService.update(announcement, announcementId);

        if (announcement1==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(announcement1);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{announcementId}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long announcementId){
        announcementService.deleteAnnouncement(announcementId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
