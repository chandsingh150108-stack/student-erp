package com.studenterp.repository;

import com.studenterp.entity.ExamRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExamRegistrationRepository extends JpaRepository<ExamRegistration, Long> {
    List<ExamRegistration> findByStudentIdAndExamId(Long studentId, Long examId);
}
