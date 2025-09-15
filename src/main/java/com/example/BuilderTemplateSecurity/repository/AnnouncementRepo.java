package com.example.BuilderTemplateSecurity.repository;

import com.example.BuilderTemplateSecurity.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement,Long> {
}
