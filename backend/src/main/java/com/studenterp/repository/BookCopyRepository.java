package com.studenterp.repository;

import com.studenterp.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    List<BookCopy> findByBookId(Long bookId);
    List<BookCopy> findByAvailabilityStatus(String status);
}
