package com.studenterp.service;

import com.studenterp.entity.Announcement;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public List<Announcement> findAll() { return announcementRepository.findAll(); }
    public List<Announcement> findRecent() { return announcementRepository.findTop10ByOrderByCreatedDateDesc(); }
    public Announcement findById(Long id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement", "id", id));
    }
    public Announcement create(Announcement announcement) { return announcementRepository.save(announcement); }
    public void delete(Long id) { announcementRepository.deleteById(id); }
}
