package com.studenterp.repository;

import com.studenterp.entity.BookIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {
    List<BookIssue> findByStudentId(Long studentId);
    List<BookIssue> findByStatus(String status);
    List<BookIssue> findByStudentIdAndStatus(Long studentId, String status);
}
