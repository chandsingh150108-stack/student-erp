package com.studenterp.repository;

import com.studenterp.entity.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ClassSessionRepository extends JpaRepository<ClassSession, Long> {
    List<ClassSession> findByFacultyIdAndDate(Long facultyId, LocalDate date);
    List<ClassSession> findBySectionId(Long sectionId);
}
