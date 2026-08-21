package com.studenterp.controller;

import com.studenterp.entity.*;
import com.studenterp.service.ScholarshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scholarships")
@RequiredArgsConstructor
public class ScholarshipController {

    private final ScholarshipService scholarshipService;

    @GetMapping
    public ResponseEntity<List<Scholarship>> findAll() { return ResponseEntity.ok(scholarshipService.findAll()); }

    @GetMapping("/active")
    public ResponseEntity<List<Scholarship>> findActive() { return ResponseEntity.ok(scholarshipService.findActive()); }

    @GetMapping("/{id}")
    public ResponseEntity<Scholarship> findById(@PathVariable Long id) { return ResponseEntity.ok(scholarshipService.findById(id)); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Scholarship> create(@RequestBody Scholarship s) { return ResponseEntity.ok(scholarshipService.create(s)); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) { scholarshipService.delete(id); return ResponseEntity.ok().build(); }

    @PostMapping("/{scholarshipId}/apply/student/{studentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<StudentScholarship> apply(@PathVariable Long scholarshipId, @PathVariable Long studentId) {
        return ResponseEntity.ok(scholarshipService.apply(scholarshipId, studentId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentScholarship>> getStudentScholarships(@PathVariable Long studentId) {
        return ResponseEntity.ok(scholarshipService.getStudentScholarships(studentId));
    }
}
