package com.studenterp.controller;

import com.studenterp.entity.Timetable;
import com.studenterp.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetable")
@RequiredArgsConstructor
public class TimetableController {

    private final TimetableService timetableService;

    @GetMapping
    public ResponseEntity<List<Timetable>> findAll() {
        return ResponseEntity.ok(timetableService.findAll());
    }

    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<Timetable>> findBySection(@PathVariable Long sectionId) {
        return ResponseEntity.ok(timetableService.findBySection(sectionId));
    }

    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<List<Timetable>> findByFaculty(@PathVariable Long facultyId) {
        return ResponseEntity.ok(timetableService.findByFaculty(facultyId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Timetable> create(@RequestBody Timetable timetable) {
        return ResponseEntity.ok(timetableService.create(timetable));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timetableService.delete(id);
        return ResponseEntity.ok().build();
    }
}
