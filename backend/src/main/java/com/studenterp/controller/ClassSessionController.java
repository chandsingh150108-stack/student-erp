package com.studenterp.controller;

import com.studenterp.entity.ClassSession;
import com.studenterp.service.ClassSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/class-sessions")
@RequiredArgsConstructor
public class ClassSessionController {

    private final ClassSessionService classSessionService;

    @GetMapping
    public ResponseEntity<List<ClassSession>> findAll() {
        return ResponseEntity.ok(classSessionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassSession> findById(@PathVariable Long id) {
        return ResponseEntity.ok(classSessionService.findById(id));
    }

    @GetMapping("/faculty/{facultyId}/date/{date}")
    public ResponseEntity<List<ClassSession>> findByFacultyAndDate(
            @PathVariable Long facultyId, @PathVariable String date) {
        return ResponseEntity.ok(classSessionService.findByFacultyAndDate(facultyId, LocalDate.parse(date)));
    }

    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<ClassSession>> findBySection(@PathVariable Long sectionId) {
        return ResponseEntity.ok(classSessionService.findBySection(sectionId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY')")
    public ResponseEntity<ClassSession> create(@RequestBody ClassSession session) {
        return ResponseEntity.ok(classSessionService.create(session));
    }
}
