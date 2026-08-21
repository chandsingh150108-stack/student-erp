package com.studenterp.service;

import com.studenterp.entity.*;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final StudentRepository studentRepository;

    public List<Complaint> findByStudent(Long studentId) { return complaintRepository.findByStudentIdOrderBySubmissionDateDesc(studentId); }
    public List<Complaint> findByStatus(String status) { return complaintRepository.findByStatus(status); }
    public List<Complaint> findAll() { return complaintRepository.findAll(); }

    public Complaint findById(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint", "id", id));
    }

    public Complaint create(Complaint complaint) {
        Student student = studentRepository.findById(complaint.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", complaint.getStudent().getId()));
        complaint.setStudent(student);
        return complaintRepository.save(complaint);
    }

    public Complaint updateStatus(Long id, String status, String resolution) {
        Complaint complaint = findById(id);
        complaint.setStatus(status);
        if (resolution != null) complaint.setResolution(resolution);
        if ("RESOLVED".equals(status) || "CLOSED".equals(status)) {
            complaint.setResolutionDate(LocalDateTime.now());
        }
        return complaintRepository.save(complaint);
    }
}
