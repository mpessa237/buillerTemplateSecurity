package com.example.BuilderTemplateSecurity.service;

import com.example.BuilderTemplateSecurity.dto.AnnouncementReqDTO;
import com.example.BuilderTemplateSecurity.dto.AnnouncementRespDTO;
import com.example.BuilderTemplateSecurity.entity.Announcement;
import com.example.BuilderTemplateSecurity.entity.Image;
import com.example.BuilderTemplateSecurity.entity.User;
import com.example.BuilderTemplateSecurity.mapper.AnnouncementMapper;
import com.example.BuilderTemplateSecurity.repository.AnnouncementRepo;
import com.example.BuilderTemplateSecurity.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final UserRepo userRepo;
    private final AnnouncementRepo announcementRepo;
    private final AnnouncementMapper announcementMapper;

    public AnnouncementRespDTO createAnnouncement(AnnouncementReqDTO announcementReqDTO, List<MultipartFile> files)throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userRepo.findByEmail(currentUserName)
                .orElseThrow(()->new RuntimeException("user not found!!"));

        Announcement announcement = announcementMapper.toEntity(announcementReqDTO);
        announcement.setUser(currentUser);

        List<Image> uploadedImages = new ArrayList<>();
        if (files !=null && !files.isEmpty()){
            for (MultipartFile file : files){
                // Créer un nom de fichier unique avec UUID pour éviter les collisions
                String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get("uploads").resolve(uniqueFileName);

                Files.copy(file.getInputStream(),filePath);
                //creer entite image et assoccier a l'annonce
                Image image = new Image();
                image.setFileName(uniqueFileName);
                image.setAnnouncement(announcement);
                uploadedImages.add(image);
            }
            announcement.setImages(uploadedImages);
        }

        Announcement savedAnnouncement = announcementRepo.save(announcement);
        return announcementMapper.toDto(savedAnnouncement);

    }

    public List<AnnouncementRespDTO> getAll(){
        List<Announcement> announcements = announcementRepo.findAll();

        return announcements.stream()
                .map(announcementMapper::toDto)
                .collect(Collectors.toList());
    }

    public AnnouncementRespDTO getAnnouncementById(Long announcementId){
        Announcement announcement = announcementRepo.findById(announcementId)
                .orElseThrow(()->new EntityNotFoundException("announcement not found!!"));
        return announcementMapper.toDto(announcement);
    }

    public List<Announcement> getAllActive(){
        return this.announcementRepo.findByActiveTrue();
    }

    public Announcement update(Announcement announcement,Long announcementId){
        Optional<Announcement> announcementOptional = announcementRepo.findById(announcementId);

        if (announcementOptional.isEmpty())
            throw new EntityNotFoundException("announcement not found!!");
        if (announcement.getTitle()!=null)
            announcementOptional.get().setTitle(announcement.getTitle());
        if (announcement.getCity()!=null)
            announcementOptional.get().setCity(announcement.getCity());
        if (announcement.getPrice()!=null)
            announcementOptional.get().setPrice(announcement.getPrice());
        if (announcement.getDescription()!=null)
            announcementOptional.get().setDescription(announcement.getDescription());
        if (announcement.getNeighborhood()!=null)
            announcementOptional.get().setNeighborhood(announcement.getNeighborhood());
        if (announcement.getTypeAnnouncement()!=null)
            announcementOptional.get().setTypeAnnouncement(announcement.getTypeAnnouncement());
        if (announcement.getImgUrl()!=null)
            announcementOptional.get().setImgUrl(announcement.getImgUrl());

        return this.announcementRepo.saveAndFlush(announcementOptional.get());
    }

    //soft delete
    public void deleteAnnouncement(Long announcementId){
        announcementRepo.findById(announcementId).ifPresent(announcement-> {
            announcement.setActive(false);
            announcementRepo.save(announcement);
        });
    }


}
