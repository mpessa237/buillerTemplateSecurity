package com.example.BuilderTemplateSecurity.service;

import com.example.BuilderTemplateSecurity.dto.AnnouncementReqDTO;
import com.example.BuilderTemplateSecurity.dto.AnnouncementRespDTO;
import com.example.BuilderTemplateSecurity.entity.Announcement;
import com.example.BuilderTemplateSecurity.entity.User;
import com.example.BuilderTemplateSecurity.mapper.AnnouncementMapper;
import com.example.BuilderTemplateSecurity.repository.AnnouncementRepo;
import com.example.BuilderTemplateSecurity.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final UserRepo userRepo;
    private final AnnouncementRepo announcementRepo;
    private final AnnouncementMapper announcementMapper;

    public AnnouncementRespDTO createAnnouncement(AnnouncementReqDTO announcementReqDTO){
        // recupere le user authentifier
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepo.findByEmail(currentUsername)
                .orElseThrow(()->new UsernameNotFoundException("User not found!!:" + currentUsername));

        Announcement announcement = announcementMapper.toEntity(announcementReqDTO);
        announcement.setUser(currentUser);

        Announcement savedAnnouncement = announcementRepo.save(announcement);

        return announcementMapper.toDto(savedAnnouncement);

    }


}
