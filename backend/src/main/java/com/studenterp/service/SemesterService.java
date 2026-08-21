package com.studenterp.service;

import com.studenterp.entity.AcademicYear;
import com.studenterp.entity.Semester;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.AcademicYearRepository;
import com.studenterp.repository.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterService {

    private final SemesterRepository semesterRepository;
    private final AcademicYearRepository academicYearRepository;

    public List<Semester> findAll() {
        return semesterRepository.findAll();
    }

    public Semester findById(Long id) {
        return semesterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semester", "id", id));
    }

    public List<Semester> findByAcademicYear(Long academicYearId) {
        return semesterRepository.findByAcademicYearId(academicYearId);
    }

    public Semester create(Semester semester) {
        AcademicYear year = academicYearRepository.findById(semester.getAcademicYear().getId())
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", "id", semester.getAcademicYear().getId()));
        semester.setAcademicYear(year);
        return semesterRepository.save(semester);
    }

    public Semester update(Long id, Semester updated) {
        Semester semester = findById(id);
        AcademicYear year = academicYearRepository.findById(updated.getAcademicYear().getId())
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", "id", updated.getAcademicYear().getId()));
        semester.setSemesterNumber(updated.getSemesterNumber());
        semester.setAcademicYear(year);
        semester.setStartDate(updated.getStartDate());
        semester.setEndDate(updated.getEndDate());
        semester.setStatus(updated.getStatus());
        return semesterRepository.save(semester);
    }

    public void delete(Long id) {
        Semester semester = findById(id);
        semesterRepository.delete(semester);
    }
}
