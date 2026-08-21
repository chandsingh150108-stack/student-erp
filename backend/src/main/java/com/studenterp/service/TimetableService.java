package com.studenterp.service;

import com.studenterp.entity.*;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimetableService {

    private final TimetableRepository timetableRepository;
    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;
    private final FacultyRepository facultyRepository;
    private final RoomRepository roomRepository;
    private final TimeSlotRepository timeSlotRepository;

    public List<Timetable> findAll() {
        return timetableRepository.findAll();
    }

    public List<Timetable> findBySection(Long sectionId) {
        return timetableRepository.findBySectionId(sectionId);
    }

    public List<Timetable> findByFaculty(Long facultyId) {
        return timetableRepository.findByFacultyId(facultyId);
    }

    public Timetable create(Timetable timetable) {
        Section section = sectionRepository.findById(timetable.getSection().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Section", "id", timetable.getSection().getId()));
        Course course = courseRepository.findById(timetable.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", timetable.getCourse().getId()));
        Faculty faculty = facultyRepository.findById(timetable.getFaculty().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Faculty", "id", timetable.getFaculty().getId()));
        Room room = roomRepository.findById(timetable.getRoom().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", timetable.getRoom().getId()));
        TimeSlot timeSlot = timeSlotRepository.findById(timetable.getTimeSlot().getId())
                .orElseThrow(() -> new ResourceNotFoundException("TimeSlot", "id", timetable.getTimeSlot().getId()));
        timetable.setSection(section);
        timetable.setCourse(course);
        timetable.setFaculty(faculty);
        timetable.setRoom(room);
        timetable.setTimeSlot(timeSlot);
        return timetableRepository.save(timetable);
    }

    public void delete(Long id) {
        timetableRepository.deleteById(id);
    }
}
