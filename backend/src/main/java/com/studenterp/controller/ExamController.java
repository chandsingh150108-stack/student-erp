package com.studenterp.controller;

import com.studenterp.entity.*;
import com.studenterp.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @GetMapping
    public ResponseEntity<List<Exam>> findAll() { return ResponseEntity.ok(examService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> findById(@PathVariable Long id) { return ResponseEntity.ok(examService.findById(id)); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Exam> create(@RequestBody Exam exam) { return ResponseEntity.ok(examService.create(exam)); }

    @GetMapping("/{id}/schedules")
    public ResponseEntity<List<ExamSchedule>> getSchedules(@PathVariable Long id) { return ResponseEntity.ok(examService.getSchedules(id)); }

    @PostMapping("/schedules")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExamSchedule> schedule(@RequestBody ExamSchedule schedule) { return ResponseEntity.ok(examService.schedule(schedule)); }

    @PostMapping("/registrations")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<ExamRegistration> register(@RequestBody ExamRegistration reg) { return ResponseEntity.ok(examService.register(reg)); }
}
