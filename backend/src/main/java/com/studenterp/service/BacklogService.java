package com.studenterp.service;

import com.studenterp.entity.*;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BacklogService {

    private final BacklogRepository backlogRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;

    public List<Backlog> findByStudent(Long studentId) { return backlogRepository.findByStudentId(studentId); }

    public Backlog create(Backlog backlog) {
        Student student = studentRepository.findById(backlog.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", backlog.getStudent().getId()));
        Course course = courseRepository.findById(backlog.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", backlog.getCourse().getId()));
        Semester semester = semesterRepository.findById(backlog.getSemester().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester", "id", backlog.getSemester().getId()));
        backlog.setStudent(student);
        backlog.setCourse(course);
        backlog.setSemester(semester);
        return backlogRepository.save(backlog);
    }

    public Backlog updateStatus(Long id, String status) {
        Backlog backlog = backlogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Backlog", "id", id));
        backlog.setStatus(status);
        return backlogRepository.save(backlog);
    }
}
