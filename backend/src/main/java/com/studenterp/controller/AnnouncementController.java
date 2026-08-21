package com.studenterp.controller;

import com.studenterp.entity.Announcement;
import com.studenterp.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    public ResponseEntity<List<Announcement>> findAll() { return ResponseEntity.ok(announcementService.findAll()); }

    @GetMapping("/recent")
    public ResponseEntity<List<Announcement>> findRecent() { return ResponseEntity.ok(announcementService.findRecent()); }

    @GetMapping("/{id}")
    public ResponseEntity<Announcement> findById(@PathVariable Long id) { return ResponseEntity.ok(announcementService.findById(id)); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY')")
    public ResponseEntity<Announcement> create(@RequestBody Announcement announcement) { return ResponseEntity.ok(announcementService.create(announcement)); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) { announcementService.delete(id); return ResponseEntity.ok().build(); }
}
