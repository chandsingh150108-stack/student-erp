package com.studenterp.service;

import com.studenterp.entity.AcademicYear;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.AcademicYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    public List<AcademicYear> findAll() {
        return academicYearRepository.findAll();
    }

    public AcademicYear findById(Long id) {
        return academicYearRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", "id", id));
    }

    public AcademicYear create(AcademicYear year) {
        if (academicYearRepository.findByYearName(year.getYearName()).isPresent()) {
            throw new IllegalArgumentException("Academic year already exists");
        }
        return academicYearRepository.save(year);
    }

    public AcademicYear update(Long id, AcademicYear updated) {
        AcademicYear year = findById(id);
        year.setYearName(updated.getYearName());
        year.setStartDate(updated.getStartDate());
        year.setEndDate(updated.getEndDate());
        year.setCurrent(updated.getCurrent());
        if (updated.getCurrent()) {
            // Reset other current years
            academicYearRepository.findAll().forEach(y -> {
                if (!y.getId().equals(id)) {
                    y.setCurrent(false);
                    academicYearRepository.save(y);
                }
            });
        }
        return academicYearRepository.save(year);
    }

    public void delete(Long id) {
        AcademicYear year = findById(id);
        academicYearRepository.delete(year);
    }
}
