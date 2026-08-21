package com.studenterp.repository;

import com.studenterp.entity.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {
    List<EventRegistration> findByStudentId(Long studentId);
    Optional<EventRegistration> findByStudentIdAndEventId(Long studentId, Long eventId);
    Boolean existsByStudentIdAndEventId(Long studentId, Long eventId);
}
