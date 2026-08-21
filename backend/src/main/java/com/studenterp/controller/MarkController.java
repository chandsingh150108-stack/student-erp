package com.studenterp.controller;

import com.studenterp.entity.Mark;
import com.studenterp.entity.Result;
import com.studenterp.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marks")
@RequiredArgsConstructor
public class MarkController {

    private final MarkService markService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Mark>> findByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(markService.findByStudent(studentId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY')")
    public ResponseEntity<Mark> enterMark(@RequestBody Mark mark) {
        return ResponseEntity.ok(markService.enterMark(mark));
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY')")
    public ResponseEntity<List<Mark>> enterBulkMarks(@RequestBody List<Mark> marks) {
        return ResponseEntity.ok(markService.enterBulkMarks(marks));
    }

    @GetMapping("/results/student/{studentId}")
    public ResponseEntity<List<Result>> getResults(@PathVariable Long studentId) {
        return ResponseEntity.ok(markService.getResults(studentId));
    }

    @PostMapping("/results")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Result> saveResult(@RequestBody Result result) {
        return ResponseEntity.ok(markService.saveResult(result));
    }
}
