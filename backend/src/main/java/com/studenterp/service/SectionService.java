package com.studenterp.service;

import com.studenterp.entity.*;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final ProgramRepository programRepository;
    private final SemesterRepository semesterRepository;
    private final AcademicYearRepository academicYearRepository;
    private final StudentSectionRepository studentSectionRepository;

    public List<Section> findAll() {
        return sectionRepository.findAll();
    }

    public Section findById(Long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "id", id));
    }

    public Section create(Section section) {
        Program program = programRepository.findById(section.getProgram().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Program", "id", section.getProgram().getId()));
        Semester semester = semesterRepository.findById(section.getSemester().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester", "id", section.getSemester().getId()));
        AcademicYear year = academicYearRepository.findById(section.getAcademicYear().getId())
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", "id", section.getAcademicYear().getId()));
        section.setProgram(program);
        section.setSemester(semester);
        section.setAcademicYear(year);
        return sectionRepository.save(section);
    }

    public Section update(Long id, Section updated) {
        Section section = findById(id);
        Program program = programRepository.findById(updated.getProgram().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Program", "id", updated.getProgram().getId()));
        Semester semester = semesterRepository.findById(updated.getSemester().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester", "id", updated.getSemester().getId()));
        AcademicYear year = academicYearRepository.findById(updated.getAcademicYear().getId())
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", "id", updated.getAcademicYear().getId()));
        section.setName(updated.getName());
        section.setCapacity(updated.getCapacity());
        section.setProgram(program);
        section.setSemester(semester);
        section.setAcademicYear(year);
        return sectionRepository.save(section);
    }

    public void delete(Long id) {
        Section section = findById(id);
        sectionRepository.delete(section);
    }

    public List<StudentSection> getEnrolledStudents(Long sectionId) {
        return studentSectionRepository.findBySectionId(sectionId);
    }
}
