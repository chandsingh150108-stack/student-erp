package com.studenterp.service;

import com.studenterp.dto.DashboardStats;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final ProgramRepository programRepository;
    private final CourseRepository courseRepository;
    private final StudentFeeRepository studentFeeRepository;
    private final ComplaintRepository complaintRepository;
    private final EventRepository eventRepository;
    private final BookRepository bookRepository;
    private final BookIssueRepository bookIssueRepository;

    public DashboardStats getStats() {
        DashboardStats stats = new DashboardStats();
        stats.setTotalStudents(studentRepository.count());
        stats.setTotalFaculty(facultyRepository.count());
        stats.setTotalDepartments(departmentRepository.count());
        stats.setTotalPrograms(programRepository.count());
        stats.setActiveCourses(courseRepository.count());
        stats.setPendingFees(studentFeeRepository.findByPaymentStatus("PENDING").size());
        stats.setOpenComplaints(complaintRepository.findByStatus("OPEN").size());
        stats.setUpcomingEvents(eventRepository.findByActiveTrueOrderByStartDateAsc().size());
        stats.setTotalBooks(bookRepository.count());
        stats.setIssuedBooks(bookIssueRepository.findByStatus("ISSUED").size());
        return stats;
    }
}
