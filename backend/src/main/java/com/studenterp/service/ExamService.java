package com.studenterp.service;

import com.studenterp.entity.*;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final ExamScheduleRepository examScheduleRepository;
    private final ExamRegistrationRepository examRegistrationRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public List<Exam> findAll() { return examRepository.findAll(); }
    public Exam findById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", id));
    }
    public Exam create(Exam exam) { return examRepository.save(exam); }

    public ExamSchedule schedule(ExamSchedule schedule) {
        Exam exam = examRepository.findById(schedule.getExam().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", schedule.getExam().getId()));
        Course course = courseRepository.findById(schedule.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", schedule.getCourse().getId()));
        schedule.setExam(exam);
        schedule.setCourse(course);
        return examScheduleRepository.save(schedule);
    }

    public List<ExamSchedule> getSchedules(Long examId) { return examScheduleRepository.findByExamId(examId); }

    public ExamRegistration register(ExamRegistration reg) {
        Student student = studentRepository.findById(reg.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", reg.getStudent().getId()));
        Exam exam = examRepository.findById(reg.getExam().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", reg.getExam().getId()));
        Course course = courseRepository.findById(reg.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", reg.getCourse().getId()));
        reg.setStudent(student);
        reg.setExam(exam);
        reg.setCourse(course);
        return examRegistrationRepository.save(reg);
    }
}
