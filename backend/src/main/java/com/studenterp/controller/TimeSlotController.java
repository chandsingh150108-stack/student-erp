package com.studenterp.controller;

import com.studenterp.entity.TimeSlot;
import com.studenterp.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-slots")
@RequiredArgsConstructor
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @GetMapping
    public ResponseEntity<List<TimeSlot>> findAll() { return ResponseEntity.ok(timeSlotService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<TimeSlot> findById(@PathVariable Long id) { return ResponseEntity.ok(timeSlotService.findById(id)); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TimeSlot> create(@RequestBody TimeSlot ts) { return ResponseEntity.ok(timeSlotService.create(ts)); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) { timeSlotService.delete(id); return ResponseEntity.ok().build(); }
}
