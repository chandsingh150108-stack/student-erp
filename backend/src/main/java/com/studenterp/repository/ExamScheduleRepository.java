package com.studenterp.repository;

import com.studenterp.entity.ExamSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExamScheduleRepository extends JpaRepository<ExamSchedule, Long> {
    List<ExamSchedule> findByExamId(Long examId);
    List<ExamSchedule> findByCourseId(Long courseId);
}
