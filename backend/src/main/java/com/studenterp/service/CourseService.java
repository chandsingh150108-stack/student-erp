package com.studenterp.service;

import com.studenterp.entity.Course;
import com.studenterp.entity.Department;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.CourseRepository;
import com.studenterp.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
    }

    public List<Course> findByDepartment(Long departmentId) {
        return courseRepository.findByDepartmentId(departmentId);
    }

    public Course create(Course course) {
        if (courseRepository.existsByCode(course.getCode())) {
            throw new IllegalArgumentException("Course code already exists");
        }
        Department dept = departmentRepository.findById(course.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", course.getDepartment().getId()));
        course.setDepartment(dept);
        return courseRepository.save(course);
    }

    public Course update(Long id, Course updated) {
        Course course = findById(id);
        Department dept = departmentRepository.findById(updated.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", updated.getDepartment().getId()));
        course.setCode(updated.getCode());
        course.setName(updated.getName());
        course.setDescription(updated.getDescription());
        course.setCredits(updated.getCredits());
        course.setCourseType(updated.getCourseType());
        course.setLectureHours(updated.getLectureHours());
        course.setTutorialHours(updated.getTutorialHours());
        course.setPracticalHours(updated.getPracticalHours());
        course.setDepartment(dept);
        return courseRepository.save(course);
    }

    public void delete(Long id) {
        Course course = findById(id);
        courseRepository.delete(course);
    }
}
