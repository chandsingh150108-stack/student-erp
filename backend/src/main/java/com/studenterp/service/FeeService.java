package com.studenterp.service;

import com.studenterp.entity.*;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeeService {

    private final FeeCategoryRepository feeCategoryRepository;
    private final FeeStructureRepository feeStructureRepository;
    private final StudentFeeRepository studentFeeRepository;
    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final ProgramRepository programRepository;
    private final SemesterRepository semesterRepository;

    public List<FeeCategory> getAllCategories() { return feeCategoryRepository.findAll(); }
    public FeeCategory createCategory(FeeCategory cat) { return feeCategoryRepository.save(cat); }

    public List<FeeStructure> getAllStructures() { return feeStructureRepository.findAll(); }
    public FeeStructure createStructure(FeeStructure fs) {
        FeeCategory cat = feeCategoryRepository.findById(fs.getFeeCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("FeeCategory", "id", fs.getFeeCategory().getId()));
        Program program = programRepository.findById(fs.getProgram().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Program", "id", fs.getProgram().getId()));
        Semester semester = semesterRepository.findById(fs.getSemester().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester", "id", fs.getSemester().getId()));
        fs.setFeeCategory(cat);
        fs.setProgram(program);
        fs.setSemester(semester);
        return feeStructureRepository.save(fs);
    }

    public List<StudentFee> getStudentFees(Long studentId) { return studentFeeRepository.findByStudentId(studentId); }

    public StudentFee assignFee(StudentFee sf) {
        Student student = studentRepository.findById(sf.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", sf.getStudent().getId()));
        FeeStructure fs = feeStructureRepository.findById(sf.getFeeStructure().getId())
                .orElseThrow(() -> new ResourceNotFoundException("FeeStructure", "id", sf.getFeeStructure().getId()));
        sf.setStudent(student);
        sf.setFeeStructure(fs);
        sf.setTotalAmount(fs.getAmount());
        sf.setRemainingBalance(fs.getAmount().subtract(sf.getAmountPaid()));
        if (sf.getRemainingBalance().compareTo(BigDecimal.ZERO) <= 0) {
            sf.setPaymentStatus("PAID");
        }
        return studentFeeRepository.save(sf);
    }

    public Payment makePayment(Long studentFeeId, Payment payment) {
        StudentFee sf = studentFeeRepository.findById(studentFeeId)
                .orElseThrow(() -> new ResourceNotFoundException("StudentFee", "id", studentFeeId));
        payment.setStudentFee(sf);
        payment = paymentRepository.save(payment);

        sf.setAmountPaid(sf.getAmountPaid().add(payment.getAmount()));
        sf.setRemainingBalance(sf.getTotalAmount().subtract(sf.getAmountPaid()));
        if (sf.getRemainingBalance().compareTo(BigDecimal.ZERO) <= 0) {
            sf.setPaymentStatus("PAID");
        } else if (sf.getAmountPaid().compareTo(BigDecimal.ZERO) > 0) {
            sf.setPaymentStatus("PARTIAL");
        }
        studentFeeRepository.save(sf);
        return payment;
    }

    public List<StudentFee> getPendingFees() { return studentFeeRepository.findByPaymentStatus("PENDING"); }
}
