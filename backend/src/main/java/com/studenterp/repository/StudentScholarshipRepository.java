package com.studenterp.repository;

import com.studenterp.entity.StudentScholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentScholarshipRepository extends JpaRepository<StudentScholarship, Long> {
    List<StudentScholarship> findByStudentId(Long studentId);
}
