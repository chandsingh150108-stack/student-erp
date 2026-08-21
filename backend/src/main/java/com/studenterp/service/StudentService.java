package com.studenterp.service;

import com.studenterp.entity.*;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final ProgramRepository programRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final StudentSectionRepository studentSectionRepository;
    private final SectionRepository sectionRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
    }

    public List<Student> search(String keyword) {
        return studentRepository.search(keyword);
    }

    public Student create(Student student) {
        if (studentRepository.existsByRollNumber(student.getRollNumber())) {
            throw new IllegalArgumentException("Roll number already exists");
        }
        Program program = programRepository.findById(student.getProgram().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Program", "id", student.getProgram().getId()));
        student.setProgram(program);

        if (student.getUser() == null || student.getUser().getId() == null) {
            User user = User.builder()
                    .username(student.getRollNumber())
                    .email(student.getEmail() != null ? student.getEmail() : student.getRollNumber() + "@college.edu")
                    .passwordHash(passwordEncoder.encode("student123"))
                    .active(true)
                    .build();
            user = userRepository.save(user);

            Role studentRole = roleRepository.findByName(Role.RoleType.STUDENT)
                    .orElseThrow(() -> new IllegalArgumentException("Student role not found"));
            userRoleRepository.save(UserRole.builder().user(user).role(studentRole).build());

            student.setUser(user);
        }

        return studentRepository.save(student);
    }

    public Student update(Long id, Student updated) {
        Student student = findById(id);
        Program program = programRepository.findById(updated.getProgram().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Program", "id", updated.getProgram().getId()));
        student.setFirstName(updated.getFirstName());
        student.setLastName(updated.getLastName());
        student.setRollNumber(updated.getRollNumber());
        student.setRegistrationNumber(updated.getRegistrationNumber());
        student.setProgram(program);
        student.setDateOfBirth(updated.getDateOfBirth());
        student.setGender(updated.getGender());
        student.setEmail(updated.getEmail());
        student.setPhone(updated.getPhone());
        student.setAdmissionDate(updated.getAdmissionDate());
        student.setCurrentSemester(updated.getCurrentSemester());
        student.setAddress(updated.getAddress());
        student.setCity(updated.getCity());
        student.setState(updated.getState());
        student.setPostalCode(updated.getPostalCode());
        student.setStatus(updated.getStatus());
        return studentRepository.save(student);
    }

    public void delete(Long id) {
        Student student = findById(id);
        studentRepository.delete(student);
    }

    public List<StudentSection> getStudentSections(Long studentId) {
        return studentSectionRepository.findByStudentId(studentId);
    }

    public StudentSection enrollInSection(Long studentId, Long sectionId) {
        Student student = findById(studentId);
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "id", sectionId));
        StudentSection ss = StudentSection.builder().student(student).section(section).build();
        return studentSectionRepository.save(ss);
    }
}
