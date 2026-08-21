package com.studenterp.repository;

import com.studenterp.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentFeeId(Long studentFeeId);
}
