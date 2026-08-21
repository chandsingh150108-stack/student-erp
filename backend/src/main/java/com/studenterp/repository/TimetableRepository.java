package com.studenterp.repository;

import com.studenterp.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    List<Timetable> findBySectionId(Long sectionId);
    List<Timetable> findByFacultyId(Long facultyId);
}
