package com.studenterp.repository;

import com.studenterp.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByClassSessionId(Long classSessionId);
    List<Attendance> findByStudentId(Long studentId);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.classSession.course.id = :courseId AND a.status = 'PRESENT'")
    Long countPresentByStudentAndCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.classSession.course.id = :courseId")
    Long countTotalByStudentAndCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
}
