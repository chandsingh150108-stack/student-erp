package com.studenterp.controller;

import com.studenterp.entity.*;
import com.studenterp.service.FeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")
@RequiredArgsConstructor
public class FeeController {

    private final FeeService feeService;

    @GetMapping("/categories")
    public ResponseEntity<List<FeeCategory>> getCategories() { return ResponseEntity.ok(feeService.getAllCategories()); }

    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FeeCategory> createCategory(@RequestBody FeeCategory cat) { return ResponseEntity.ok(feeService.createCategory(cat)); }

    @GetMapping("/structures")
    public ResponseEntity<List<FeeStructure>> getStructures() { return ResponseEntity.ok(feeService.getAllStructures()); }

    @PostMapping("/structures")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FeeStructure> createStructure(@RequestBody FeeStructure fs) { return ResponseEntity.ok(feeService.createStructure(fs)); }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentFee>> getStudentFees(@PathVariable Long studentId) { return ResponseEntity.ok(feeService.getStudentFees(studentId)); }

    @PostMapping("/student-fees")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentFee> assignFee(@RequestBody StudentFee sf) { return ResponseEntity.ok(feeService.assignFee(sf)); }

    @PostMapping("/student-fees/{studentFeeId}/payments")
    public ResponseEntity<Payment> makePayment(@PathVariable Long studentFeeId, @RequestBody Payment payment) { return ResponseEntity.ok(feeService.makePayment(studentFeeId, payment)); }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StudentFee>> getPendingFees() { return ResponseEntity.ok(feeService.getPendingFees()); }
}
