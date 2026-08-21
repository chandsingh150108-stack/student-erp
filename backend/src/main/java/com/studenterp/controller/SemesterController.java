package com.studenterp.controller;

import com.studenterp.entity.Semester;
import com.studenterp.service.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semesters")
@RequiredArgsConstructor
public class SemesterController {

    private final SemesterService semesterService;

    @GetMapping
    public ResponseEntity<List<Semester>> findAll() {
        return ResponseEntity.ok(semesterService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Semester> findById(@PathVariable Long id) {
        return ResponseEntity.ok(semesterService.findById(id));
    }

    @GetMapping("/academic-year/{academicYearId}")
    public ResponseEntity<List<Semester>> findByAcademicYear(@PathVariable Long academicYearId) {
        return ResponseEntity.ok(semesterService.findByAcademicYear(academicYearId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Semester> create(@RequestBody Semester semester) {
        return ResponseEntity.ok(semesterService.create(semester));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Semester> update(@PathVariable Long id, @RequestBody Semester semester) {
        return ResponseEntity.ok(semesterService.update(id, semester));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        semesterService.delete(id);
        return ResponseEntity.ok().build();
    }
}
