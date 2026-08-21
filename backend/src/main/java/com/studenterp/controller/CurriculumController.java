package com.studenterp.controller;

import com.studenterp.entity.Curriculum;
import com.studenterp.service.CurriculumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/curriculum")
@RequiredArgsConstructor
public class CurriculumController {

    private final CurriculumService curriculumService;

    @GetMapping
    public ResponseEntity<List<Curriculum>> findAll() {
        return ResponseEntity.ok(curriculumService.findAll());
    }

    @GetMapping("/program/{programId}/semester/{semesterId}")
    public ResponseEntity<List<Curriculum>> findByProgramAndSemester(
            @PathVariable Long programId, @PathVariable Long semesterId) {
        return ResponseEntity.ok(curriculumService.findByProgramAndSemester(programId, semesterId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Curriculum> create(@RequestBody Curriculum curriculum) {
        return ResponseEntity.ok(curriculumService.create(curriculum));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        curriculumService.delete(id);
        return ResponseEntity.ok().build();
    }
}
