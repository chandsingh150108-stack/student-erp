package com.studenterp.controller;

import com.studenterp.entity.Attendance;
import com.studenterp.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/class-session/{classSessionId}")
    public ResponseEntity<List<Attendance>> findByClassSession(@PathVariable Long classSessionId) {
        return ResponseEntity.ok(attendanceService.findByClassSession(classSessionId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> findByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.findByStudent(studentId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY')")
    public ResponseEntity<Attendance> record(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.record(attendance));
    }

    @PostMapping("/bulk/{classSessionId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY')")
    public ResponseEntity<List<Attendance>> recordBulk(
            @PathVariable Long classSessionId, @RequestBody List<Attendance> attendances) {
        return ResponseEntity.ok(attendanceService.recordBulk(classSessionId, attendances));
    }

    @GetMapping("/percentage/student/{studentId}/course/{courseId}")
    public ResponseEntity<Map<String, Double>> getPercentage(
            @PathVariable Long studentId, @PathVariable Long courseId) {
        double percentage = attendanceService.getAttendancePercentage(studentId, courseId);
        return ResponseEntity.ok(Map.of("percentage", percentage));
    }
}
