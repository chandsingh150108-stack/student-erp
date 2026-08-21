package com.studenterp.service;

import com.studenterp.entity.*;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;
    private final StudentScholarshipRepository studentScholarshipRepository;
    private final StudentRepository studentRepository;

    public List<Scholarship> findAll() { return scholarshipRepository.findAll(); }
    public List<Scholarship> findActive() { return scholarshipRepository.findByActiveTrue(); }
    public Scholarship findById(Long id) {
        return scholarshipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scholarship", "id", id));
    }
    public Scholarship create(Scholarship s) { return scholarshipRepository.save(s); }
    public void delete(Long id) { scholarshipRepository.deleteById(id); }

    public StudentScholarship apply(Long scholarshipId, Long studentId) {
        Scholarship s = findById(scholarshipId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
        StudentScholarship ss = StudentScholarship.builder()
                .student(student).scholarship(s)
                .applicationStatus("APPLIED").build();
        return studentScholarshipRepository.save(ss);
    }

    public List<StudentScholarship> getStudentScholarships(Long studentId) {
        return studentScholarshipRepository.findByStudentId(studentId);
    }
}
