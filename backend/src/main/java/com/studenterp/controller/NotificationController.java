package com.studenterp.controller;

import com.studenterp.entity.Notification;
import com.studenterp.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> findByUser(@PathVariable Long userId) { return ResponseEntity.ok(notificationService.findByUser(userId)); }

    @GetMapping("/user/{userId}/unread-count")
    public ResponseEntity<Map<String, Long>> countUnread(@PathVariable Long userId) { return ResponseEntity.ok(Map.of("count", notificationService.countUnread(userId))); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Notification> create(@RequestBody Notification notification) { return ResponseEntity.ok(notificationService.create(notification)); }

    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) { return ResponseEntity.ok(notificationService.markAsRead(id)); }
}
