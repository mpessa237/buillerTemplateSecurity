package com.example.BuilderTemplateSecurity.dto;

import com.example.BuilderTemplateSecurity.entity.PropertyType;
import com.example.BuilderTemplateSecurity.entity.TypeAnnouncement;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementRespDTO {
    private Long announcementId;
    private String title;
    private String description;
    private String neighborhood;
    private String city;
    private Double price;
    private String imgUrl;
    @JsonProperty("property_type")
    private PropertyType propertyType;
    @JsonProperty("type_announcement")
    private TypeAnnouncement typeAnnouncement;
    private String userName;

}
