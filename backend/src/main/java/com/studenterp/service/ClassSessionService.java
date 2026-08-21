package com.studenterp.service;

import com.studenterp.entity.*;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassSessionService {

    private final ClassSessionRepository classSessionRepository;
    private final CourseRepository courseRepository;
    private final FacultyRepository facultyRepository;
    private final SectionRepository sectionRepository;

    public List<ClassSession> findAll() {
        return classSessionRepository.findAll();
    }

    public ClassSession findById(Long id) {
        return classSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClassSession", "id", id));
    }

    public List<ClassSession> findByFacultyAndDate(Long facultyId, LocalDate date) {
        return classSessionRepository.findByFacultyIdAndDate(facultyId, date);
    }

    public List<ClassSession> findBySection(Long sectionId) {
        return classSessionRepository.findBySectionId(sectionId);
    }

    public ClassSession create(ClassSession session) {
        Course course = courseRepository.findById(session.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", session.getCourse().getId()));
        Faculty faculty = facultyRepository.findById(session.getFaculty().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Faculty", "id", session.getFaculty().getId()));
        Section section = sectionRepository.findById(session.getSection().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Section", "id", session.getSection().getId()));
        session.setCourse(course);
        session.setFaculty(faculty);
        session.setSection(section);
        return classSessionRepository.save(session);
    }
}
