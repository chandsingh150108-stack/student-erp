package com.studenterp.controller;

import com.studenterp.entity.Complaint;
import com.studenterp.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Complaint>> findAll() { return ResponseEntity.ok(complaintService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<Complaint> findById(@PathVariable Long id) { return ResponseEntity.ok(complaintService.findById(id)); }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Complaint>> findByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(complaintService.findByStudent(studentId));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Complaint>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(complaintService.findByStatus(status));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<Complaint> create(@RequestBody Complaint complaint) { return ResponseEntity.ok(complaintService.create(complaint)); }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Complaint> updateStatus(@PathVariable Long id, @RequestParam String status, @RequestParam(required = false) String resolution) {
        return ResponseEntity.ok(complaintService.updateStatus(id, status, resolution));
    }
}
