package com.studenterp.controller;

import com.studenterp.entity.Backlog;
import com.studenterp.service.BacklogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backlogs")
@RequiredArgsConstructor
public class BacklogController {

    private final BacklogService backlogService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Backlog>> findByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(backlogService.findByStudent(studentId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Backlog> create(@RequestBody Backlog backlog) { return ResponseEntity.ok(backlogService.create(backlog)); }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Backlog> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(backlogService.updateStatus(id, status));
    }
}
