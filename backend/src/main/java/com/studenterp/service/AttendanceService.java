package com.studenterp.service;

import com.studenterp.entity.*;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ClassSessionRepository classSessionRepository;
    private final StudentRepository studentRepository;

    public List<Attendance> findByClassSession(Long classSessionId) {
        return attendanceRepository.findByClassSessionId(classSessionId);
    }

    public List<Attendance> findByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    public Attendance record(Attendance attendance) {
        ClassSession session = classSessionRepository.findById(attendance.getClassSession().getId())
                .orElseThrow(() -> new ResourceNotFoundException("ClassSession", "id", attendance.getClassSession().getId()));
        Student student = studentRepository.findById(attendance.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", attendance.getStudent().getId()));
        attendance.setClassSession(session);
        attendance.setStudent(student);
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> recordBulk(Long classSessionId, List<Attendance> attendances) {
        ClassSession session = classSessionRepository.findById(classSessionId)
                .orElseThrow(() -> new ResourceNotFoundException("ClassSession", "id", classSessionId));
        for (Attendance a : attendances) {
            Student student = studentRepository.findById(a.getStudent().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student", "id", a.getStudent().getId()));
            a.setClassSession(session);
            a.setStudent(student);
        }
        return attendanceRepository.saveAll(attendances);
    }

    public double getAttendancePercentage(Long studentId, Long courseId) {
        Long present = attendanceRepository.countPresentByStudentAndCourse(studentId, courseId);
        Long total = attendanceRepository.countTotalByStudentAndCourse(studentId, courseId);
        if (total == 0) return 0.0;
        return (present * 100.0) / total;
    }
}
