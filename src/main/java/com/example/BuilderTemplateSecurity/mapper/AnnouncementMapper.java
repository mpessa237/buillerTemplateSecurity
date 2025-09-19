package com.example.BuilderTemplateSecurity.mapper;

import com.example.BuilderTemplateSecurity.dto.AnnouncementReqDTO;
import com.example.BuilderTemplateSecurity.dto.AnnouncementRespDTO;
import com.example.BuilderTemplateSecurity.entity.Announcement;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementMapper {

    public Announcement toEntity(AnnouncementReqDTO announcementReqDTO){
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementReqDTO.getTitle());
        announcement.setDescription(announcementReqDTO.getDescription());
        announcement.setNeighborhood(announcementReqDTO.getNeighborhood());
        announcement.setCity(announcementReqDTO.getCity());
        announcement.setImgUrl(announcementReqDTO.getImgUrl());
        announcement.setPrice(announcementReqDTO.getPrice());
        announcement.setPropertyType(announcementReqDTO.getPropertyType());
        announcement.setTypeAnnouncement(announcementReqDTO.getTypeAnnouncement());

        return announcement;
    }

    public AnnouncementRespDTO toDto(Announcement announcement){
        if (announcement==null){
            return null;
        }
        AnnouncementRespDTO announcementRespDTO = new AnnouncementRespDTO();
        announcementRespDTO.setAnnouncementId(announcement.getAnnouncementId());
        announcementRespDTO.setTitle(announcement.getTitle());
        announcementRespDTO.setDescription(announcement.getDescription());
        announcementRespDTO.setNeighborhood(announcement.getNeighborhood());
        announcementRespDTO.setCity(announcement.getCity());
        announcementRespDTO.setImgUrl(announcement.getImgUrl());
        announcementRespDTO.setPropertyType(announcement.getPropertyType());
        announcementRespDTO.setPrice(announcement.getPrice());
        announcementRespDTO.setTypeAnnouncement(announcement.getTypeAnnouncement());

        if (announcement.getUser() !=null){
            announcementRespDTO.setUserName(announcement.getUser().getFirstname() + " " + announcement.getUser().getLastname());
        }

        return announcementRespDTO;

    }
}
