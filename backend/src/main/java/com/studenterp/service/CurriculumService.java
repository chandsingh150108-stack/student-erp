package com.studenterp.service;

import com.studenterp.entity.*;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurriculumService {

    private final CurriculumRepository curriculumRepository;
    private final ProgramRepository programRepository;
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;

    public List<Curriculum> findAll() {
        return curriculumRepository.findAll();
    }

    public List<Curriculum> findByProgramAndSemester(Long programId, Long semesterId) {
        return curriculumRepository.findByProgramIdAndSemesterId(programId, semesterId);
    }

    public Curriculum create(Curriculum curriculum) {
        Program program = programRepository.findById(curriculum.getProgram().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Program", "id", curriculum.getProgram().getId()));
        Semester semester = semesterRepository.findById(curriculum.getSemester().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester", "id", curriculum.getSemester().getId()));
        Course course = courseRepository.findById(curriculum.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", curriculum.getCourse().getId()));
        curriculum.setProgram(program);
        curriculum.setSemester(semester);
        curriculum.setCourse(course);
        return curriculumRepository.save(curriculum);
    }

    public void delete(Long id) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curriculum", "id", id));
        curriculumRepository.delete(curriculum);
    }
}
