package com.studenterp.repository;

import com.studenterp.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByStudentIdOrderBySubmissionDateDesc(Long studentId);
    List<Complaint> findByStatus(String status);
}
