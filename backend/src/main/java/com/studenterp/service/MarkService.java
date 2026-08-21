package com.studenterp.service;

import com.studenterp.entity.*;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkService {

    private final MarkRepository markRepository;
    private final ResultRepository resultRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;

    public List<Mark> findByStudent(Long studentId) { return markRepository.findByStudentId(studentId); }

    public Mark enterMark(Mark mark) {
        Student student = studentRepository.findById(mark.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", mark.getStudent().getId()));
        Exam exam = examRepository.findById(mark.getExam().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", mark.getExam().getId()));
        Course course = courseRepository.findById(mark.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", mark.getCourse().getId()));
        mark.setStudent(student);
        mark.setExam(exam);
        mark.setCourse(course);
        return markRepository.save(mark);
    }

    public List<Mark> enterBulkMarks(List<Mark> marks) {
        return markRepository.saveAll(marks);
    }

    public List<Result> getResults(Long studentId) { return resultRepository.findByStudentId(studentId); }

    public Result saveResult(Result result) {
        Student student = studentRepository.findById(result.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", result.getStudent().getId()));
        Semester semester = semesterRepository.findById(result.getSemester().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester", "id", result.getSemester().getId()));
        result.setStudent(student);
        result.setSemester(semester);
        return resultRepository.save(result);
    }
}
