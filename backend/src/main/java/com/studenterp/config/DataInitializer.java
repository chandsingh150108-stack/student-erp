package com.studenterp.config;

import com.studenterp.entity.*;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final DepartmentRepository departmentRepository;
    private final ProgramRepository programRepository;
    private final AcademicYearRepository academicYearRepository;
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;
    private final CurriculumRepository curriculumRepository;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final SectionRepository sectionRepository;
    private final StudentSectionRepository studentSectionRepository;
    private final FacultyCourseRepository facultyCourseRepository;
    private final RoomRepository roomRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final TimetableRepository timetableRepository;
    private final ExamRepository examRepository;
    private final ExamScheduleRepository examScheduleRepository;
    private final FeeCategoryRepository feeCategoryRepository;
    private final FeeStructureRepository feeStructureRepository;
    private final StudentFeeRepository studentFeeRepository;
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final AnnouncementRepository announcementRepository;
    private final EventRepository eventRepository;
    private final ScholarshipRepository scholarshipRepository;
    private final ComplaintRepository complaintRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        // Roles
        Role adminRole = roleRepository.save(Role.builder().name(Role.RoleType.ADMIN).build());
        Role facultyRole = roleRepository.save(Role.builder().name(Role.RoleType.FACULTY).build());
        Role studentRole = roleRepository.save(Role.builder().name(Role.RoleType.STUDENT).build());

        // Admin user
        User adminUser = userRepository.save(User.builder()
                .username("admin").email("admin@college.edu")
                .passwordHash(passwordEncoder.encode("admin123")).active(true).build());
        userRoleRepository.save(UserRole.builder().user(adminUser).role(adminRole).build());

        // Departments
        Department cse = departmentRepository.save(Department.builder().code("CSE").name("Computer Science and Engineering").description("CSE Department").active(true).build());
        Department aiml = departmentRepository.save(Department.builder().code("AIML").name("Artificial Intelligence and Machine Learning").description("AIML Department").active(true).build());
        Department ece = departmentRepository.save(Department.builder().code("ECE").name("Electronics and Communication Engineering").description("ECE Department").active(true).build());

        // Programs
        Program btechCse = programRepository.save(Program.builder().department(cse).code("BTECH-CSE").name("B.Tech Computer Science and Engineering").degreeType("B.Tech").durationYears(4).totalCredits(160).active(true).build());
        Program btechAiml = programRepository.save(Program.builder().department(aiml).code("BTECH-AIML").name("B.Tech AI and Machine Learning").degreeType("B.Tech").durationYears(4).totalCredits(160).active(true).build());

        // Academic Year
        AcademicYear ay = academicYearRepository.save(AcademicYear.builder().yearName("2025-2026").startDate(LocalDate.of(2025, 8, 1)).endDate(LocalDate.of(2026, 7, 31)).current(true).build());

        // Semesters
        Semester sem1 = semesterRepository.save(Semester.builder().semesterNumber(1).academicYear(ay).startDate(LocalDate.of(2025, 8, 1)).endDate(LocalDate.of(2025, 12, 15)).status("COMPLETED").build());
        Semester sem2 = semesterRepository.save(Semester.builder().semesterNumber(2).academicYear(ay).startDate(LocalDate.of(2026, 1, 5)).endDate(LocalDate.of(2026, 5, 30)).status("ACTIVE").build());

        // Courses
        Course cs101 = courseRepository.save(Course.builder().department(cse).code("CS101").name("Programming Fundamentals").description("Intro to programming").credits(4).courseType("CORE").lectureHours(3).tutorialHours(1).practicalHours(2).build());
        Course cs102 = courseRepository.save(Course.builder().department(cse).code("CS102").name("Data Structures").description("Data structures and algorithms").credits(4).courseType("CORE").lectureHours(3).tutorialHours(1).practicalHours(2).build());
        Course cs201 = courseRepository.save(Course.builder().department(cse).code("CS201").name("Database Systems").description("RDBMS concepts").credits(3).courseType("CORE").lectureHours(3).tutorialHours(0).practicalHours(0).build());
        Course ma101 = courseRepository.save(Course.builder().department(cse).code("MA101").name("Engineering Mathematics I").description("Calculus and Linear Algebra").credits(4).courseType("CORE").lectureHours(4).tutorialHours(0).practicalHours(0).build());
        Course ai101 = courseRepository.save(Course.builder().department(aiml).code("AI101").name("Introduction to AI").description("AI fundamentals").credits(3).courseType("CORE").lectureHours(3).tutorialHours(0).practicalHours(0).build());

        // Curriculum
        curriculumRepository.save(Curriculum.builder().program(btechCse).semester(sem1).course(cs101).sequenceOrder(1).build());
        curriculumRepository.save(Curriculum.builder().program(btechCse).semester(sem1).course(ma101).sequenceOrder(2).build());
        curriculumRepository.save(Curriculum.builder().program(btechCse).semester(sem2).course(cs102).sequenceOrder(1).build());
        curriculumRepository.save(Curriculum.builder().program(btechCse).semester(sem2).course(cs201).sequenceOrder(2).build());

        // Faculty
        User f1User = userRepository.save(User.builder().username("faculty1").email("faculty1@college.edu").passwordHash(passwordEncoder.encode("faculty123")).active(true).build());
        userRoleRepository.save(UserRole.builder().user(f1User).role(facultyRole).build());
        Faculty fac1 = facultyRepository.save(Faculty.builder().user(f1User).department(cse).employeeNumber("EMP001").firstName("Rajesh").lastName("Kumar").email("rajesh@college.edu").phone("9876543210").designation("Professor").specialization("Computer Science").joiningDate(LocalDate.of(2015, 7, 1)).employmentStatus("ACTIVE").build());

        User f2User = userRepository.save(User.builder().username("faculty2").email("faculty2@college.edu").passwordHash(passwordEncoder.encode("faculty123")).active(true).build());
        userRoleRepository.save(UserRole.builder().user(f2User).role(facultyRole).build());
        Faculty fac2 = facultyRepository.save(Faculty.builder().user(f2User).department(aiml).employeeNumber("EMP002").firstName("Priya").lastName("Sharma").email("priya@college.edu").phone("9876543211").designation("Associate Professor").specialization("Machine Learning").joiningDate(LocalDate.of(2018, 1, 15)).employmentStatus("ACTIVE").build());

        // Sections
        Section secA = sectionRepository.save(Section.builder().program(btechCse).semester(sem2).academicYear(ay).name("CSE-A").capacity(60).build());
        Section secB = sectionRepository.save(Section.builder().program(btechCse).semester(sem2).academicYear(ay).name("CSE-B").capacity(60).build());

        // Students
        for (int i = 1; i <= 10; i++) {
            User sUser = userRepository.save(User.builder()
                    .username("student" + i).email("student" + i + "@college.edu")
                    .passwordHash(passwordEncoder.encode("student123")).active(true).build());
            userRoleRepository.save(UserRole.builder().user(sUser).role(studentRole).build());
            Student student = studentRepository.save(Student.builder()
                    .user(sUser).program(btechCse).rollNumber("AIML2025" + String.format("%03d", i))
                    .registrationNumber("REG2025" + String.format("%03d", i))
                    .firstName("Student" + i).lastName("User")
                    .dateOfBirth(LocalDate.of(2003, 1, i))
                    .gender(i % 2 == 0 ? "Male" : "Female")
                    .email("student" + i + "@college.edu").phone("987654321" + i)
                    .admissionDate(LocalDate.of(2025, 8, 1)).currentSemester(2)
                    .status("ACTIVE").build());
            studentSectionRepository.save(StudentSection.builder().student(student).section(secA).build());
        }

        // Faculty Courses
        facultyCourseRepository.save(FacultyCourse.builder().faculty(fac1).course(cs101).section(secA).academicYear(ay).build());
        facultyCourseRepository.save(FacultyCourse.builder().faculty(fac1).course(cs102).section(secA).academicYear(ay).build());
        facultyCourseRepository.save(FacultyCourse.builder().faculty(fac2).course(ai101).section(secA).academicYear(ay).build());

        // Rooms
        Room r1 = roomRepository.save(Room.builder().roomNumber("101").building("Main Block").roomType("CLASSROOM").capacity(60).build());
        Room r2 = roomRepository.save(Room.builder().roomNumber("201").building("Main Block").roomType("LAB").capacity(40).build());

        // Time Slots
        TimeSlot ts1 = timeSlotRepository.save(TimeSlot.builder().label("09:00-10:00").startTime(LocalTime.of(9, 0)).endTime(LocalTime.of(10, 0)).build());
        TimeSlot ts2 = timeSlotRepository.save(TimeSlot.builder().label("10:00-11:00").startTime(LocalTime.of(10, 0)).endTime(LocalTime.of(11, 0)).build());
        TimeSlot ts3 = timeSlotRepository.save(TimeSlot.builder().label("11:00-12:00").startTime(LocalTime.of(11, 0)).endTime(LocalTime.of(12, 0)).build());
        TimeSlot ts4 = timeSlotRepository.save(TimeSlot.builder().label("14:00-15:00").startTime(LocalTime.of(14, 0)).endTime(LocalTime.of(15, 0)).build());

        // Timetable
        timetableRepository.save(Timetable.builder().section(secA).course(cs101).faculty(fac1).room(r1).timeSlot(ts1).dayOfWeek("MONDAY").build());
        timetableRepository.save(Timetable.builder().section(secA).course(cs102).faculty(fac1).room(r1).timeSlot(ts2).dayOfWeek("MONDAY").build());
        timetableRepository.save(Timetable.builder().section(secA).course(cs201).faculty(fac2).room(r2).timeSlot(ts3).dayOfWeek("TUESDAY").build());
        timetableRepository.save(Timetable.builder().section(secA).course(ai101).faculty(fac2).room(r1).timeSlot(ts1).dayOfWeek("WEDNESDAY").build());

        // Exams
        Exam mid = examRepository.save(Exam.builder().name("Mid Semester Examination").description("Mid sem exams").examType("MID_SEM").maxMarks(50).passingMarks(20).build());
        Exam end = examRepository.save(Exam.builder().name("End Semester Examination").description("End sem exams").examType("END_SEM").maxMarks(100).passingMarks(40).build());
        examScheduleRepository.save(ExamSchedule.builder().exam(mid).course(cs101).date(LocalDate.of(2025, 10, 15)).startTime(LocalTime.of(9, 0)).endTime(LocalTime.of(11, 0)).room("101").build());
        examScheduleRepository.save(ExamSchedule.builder().exam(end).course(cs101).date(LocalDate.of(2025, 12, 10)).startTime(LocalTime.of(9, 0)).endTime(LocalTime.of(12, 0)).room("101").build());

        // Fee Categories
        FeeCategory tuition = feeCategoryRepository.save(FeeCategory.builder().name("Tuition Fee").description("Semester tuition fee").active(true).build());
        FeeCategory examFee = feeCategoryRepository.save(FeeCategory.builder().name("Examination Fee").description("Exam fee").active(true).build());
        FeeCategory libFee = feeCategoryRepository.save(FeeCategory.builder().name("Library Fee").description("Library fee").active(true).build());

        // Fee Structures
        FeeStructure fsTuition = feeStructureRepository.save(FeeStructure.builder().feeCategory(tuition).program(btechCse).semester(sem2).amount(new BigDecimal("50000")).build());
        FeeStructure fsExam = feeStructureRepository.save(FeeStructure.builder().feeCategory(examFee).program(btechCse).semester(sem2).amount(new BigDecimal("2000")).build());

        // Student Fees - for first student
        Student firstStudent = studentRepository.findAll().get(0);
        studentFeeRepository.save(StudentFee.builder().student(firstStudent).feeStructure(fsTuition).totalAmount(new BigDecimal("50000")).amountPaid(new BigDecimal("25000")).remainingBalance(new BigDecimal("25000")).paymentStatus("PARTIAL").build());
        studentFeeRepository.save(StudentFee.builder().student(firstStudent).feeStructure(fsExam).totalAmount(new BigDecimal("2000")).amountPaid(BigDecimal.ZERO).remainingBalance(new BigDecimal("2000")).paymentStatus("PENDING").build());

        // Books
        Book b1 = bookRepository.save(Book.builder().isbn("978-0134685991").title("Effective Java").author("Joshua Bloch").publisher("Addison-Wesley").category("Programming").active(true).build());
        Book b2 = bookRepository.save(Book.builder().isbn("978-0262033848").title("Introduction to Algorithms").author("Thomas Cormen").publisher("MIT Press").category("Algorithms").active(true).build());
        bookCopyRepository.save(BookCopy.builder().book(b1).copyNumber("CP001").availabilityStatus("AVAILABLE").build());
        bookCopyRepository.save(BookCopy.builder().book(b1).copyNumber("CP002").availabilityStatus("AVAILABLE").build());
        bookCopyRepository.save(BookCopy.builder().book(b2).copyNumber("CP003").availabilityStatus("AVAILABLE").build());

        // Announcements
        announcementRepository.save(Announcement.builder().title("Mid Semester Exam Schedule Published").content("The mid semester examination schedule has been published. Please check the exam portal for details.").author("Examination Cell").build());
        announcementRepository.save(Announcement.builder().title("Holiday Notice - Republic Day").content("College will remain closed on January 26th for Republic Day celebrations.").author("Administration").build());
        announcementRepository.save(Announcement.builder().title("Placement Drive - TCS").content("TCS will be conducting a placement drive on February 15th. Eligible students should register.").author("Training & Placement Cell").build());

        // Events
        eventRepository.save(Event.builder().name("TechFest 2026").description("Annual technical festival").startDate(LocalDate.of(2026, 3, 15)).endDate(LocalDate.of(2026, 3, 17)).venue("College Auditorium").registrationDeadline(java.time.LocalDateTime.of(2026, 3, 10, 23, 59)).active(true).build());
        eventRepository.save(Event.builder().name("Hackathon").description("24-hour coding hackathon").startDate(LocalDate.of(2026, 2, 20)).endDate(LocalDate.of(2026, 2, 21)).venue("Computer Lab").registrationDeadline(java.time.LocalDateTime.of(2026, 2, 18, 23, 59)).active(true).build());

        // Scholarships
        scholarshipRepository.save(Scholarship.builder().name("Merit Scholarship").description("For students with CGPA > 9.0").amount(new BigDecimal("25000")).eligibilityInfo("CGPA > 9.0").applicationDeadline(LocalDate.of(2026, 3, 31)).active(true).build());
        scholarshipRepository.save(Scholarship.builder().name("Need-based Scholarship").description("For economically weaker students").amount(new BigDecimal("15000")).eligibilityInfo("Family income < 3 LPA").applicationDeadline(LocalDate.of(2026, 3, 31)).active(true).build());

        // Complaints
        complaintRepository.save(Complaint.builder().student(firstStudent).subject("WiFi not working in hostel").description("WiFi has been down for 3 days in Block A hostel.").status("OPEN").build());

        System.out.println("Sample data loaded successfully!");
    }
}
