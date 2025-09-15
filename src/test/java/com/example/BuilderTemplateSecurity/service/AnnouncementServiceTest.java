package com.example.BuilderTemplateSecurity.service;

import com.example.BuilderTemplateSecurity.dto.AnnouncementReqDTO;
import com.example.BuilderTemplateSecurity.dto.AnnouncementRespDTO;
import com.example.BuilderTemplateSecurity.entity.Announcement;
import com.example.BuilderTemplateSecurity.entity.TypeAnnouncement;
import com.example.BuilderTemplateSecurity.entity.User;
import com.example.BuilderTemplateSecurity.mapper.AnnouncementMapper;
import com.example.BuilderTemplateSecurity.repository.AnnouncementRepo;
import com.example.BuilderTemplateSecurity.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnnouncementServiceTest {

    @Mock
    private UserRepo userRepo;
    @Mock
    private AnnouncementMapper announcementMapper;
    @Mock
    private AnnouncementRepo announcementRepo;
    @InjectMocks
    private AnnouncementService announcementService;

    @BeforeEach
    void setup() {
        // Préparer le contexte de sécurité avant chaque test
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("testuser@example.com");
    }

    @Test
    void testShouldSaveNewAnnouncement(){

        //Given
        AnnouncementReqDTO reqDTO = new AnnouncementReqDTO("Titre Test", "Description Test", "Neighborhood Test", "City Test", 100.0, "img.jpg", TypeAnnouncement.SALE);
        User user = new User("John", "Doe", "testuser@example.com", "password");
        Announcement announcementToSave = new Announcement(null, "Titre Test", "Description Test", "Neighborhood Test", "City Test", 100.0, "img.jpg", TypeAnnouncement.SALE, user);
        Announcement savedAnnouncement = new Announcement(1L, "Titre Test", "Description Test", "Neighborhood Test", "City Test", 100.0, "img.jpg", TypeAnnouncement.SALE, user);
        AnnouncementRespDTO respDTO = new AnnouncementRespDTO(1L, "Titre Test", "Description Test", "Neighborhood Test", "City Test", 100.0, "img.jpg", TypeAnnouncement.SALE);

        //When
        // 2. Définir le comportement des mocks
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(announcementMapper.toEntity(any(AnnouncementReqDTO.class))).thenReturn(announcementToSave);
        when(announcementRepo.save(any(Announcement.class))).thenReturn(savedAnnouncement);
        when(announcementMapper.toDto(any(Announcement.class))).thenReturn(respDTO);
        //Then
        // 3. Exécuter la méthode à tester
        AnnouncementRespDTO result = announcementService.createAnnouncement(reqDTO);

        // 4. Vérifier les résultats
        assertNotNull(result);
        assertEquals(1L, result.getAnnouncementId());
        assertEquals("Titre Test", result.getTitle());

        // 5. Vérifier les interactions avec les mocks
        verify(userRepo, times(1)).findByEmail("testuser@example.com");
        verify(announcementMapper, times(1)).toEntity(reqDTO);
        verify(announcementRepo, times(1)).save(announcementToSave);
        verify(announcementMapper, times(1)).toDto(savedAnnouncement);
    }


}