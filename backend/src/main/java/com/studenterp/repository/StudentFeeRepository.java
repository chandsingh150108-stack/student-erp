package com.studenterp.repository;

import com.studenterp.entity.StudentFee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentFeeRepository extends JpaRepository<StudentFee, Long> {
    List<StudentFee> findByStudentId(Long studentId);
    List<StudentFee> findByPaymentStatus(String status);
}
