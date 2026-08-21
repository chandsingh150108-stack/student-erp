package com.studenterp.controller;

import com.studenterp.entity.*;
import com.studenterp.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> findAll() { return ResponseEntity.ok(eventService.findAll()); }

    @GetMapping("/active")
    public ResponseEntity<List<Event>> findActive() { return ResponseEntity.ok(eventService.findActive()); }

    @GetMapping("/{id}")
    public ResponseEntity<Event> findById(@PathVariable Long id) { return ResponseEntity.ok(eventService.findById(id)); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Event> create(@RequestBody Event event) { return ResponseEntity.ok(eventService.create(event)); }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Event> update(@PathVariable Long id, @RequestBody Event event) { return ResponseEntity.ok(eventService.update(id, event)); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) { eventService.delete(id); return ResponseEntity.ok().build(); }

    @PostMapping("/{eventId}/register/student/{studentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<EventRegistration> register(@PathVariable Long eventId, @PathVariable Long studentId) {
        return ResponseEntity.ok(eventService.register(eventId, studentId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EventRegistration>> getStudentRegistrations(@PathVariable Long studentId) {
        return ResponseEntity.ok(eventService.getStudentRegistrations(studentId));
    }
}
