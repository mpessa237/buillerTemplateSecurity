package com.example.BuilderTemplateSecurity.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "announcements")
public class Announcement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcementId;
    private String title;
    private String description;
    private String neighborhood;
    private String city;
    private Double price;
    private String imgUrl;
    private boolean active  = true;
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;
    @Enumerated(EnumType.STRING)
    @JsonProperty("type_announcement")
    private TypeAnnouncement typeAnnouncement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    //@JsonBackReference .pour la reference circle
    private User user;
    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    public Announcement(Long l, String titreTest, String descriptionTest, String neighborhoodTest, String cityTest, double v, String image, TypeAnnouncement typeAnnouncement, User user) {
    }
}
