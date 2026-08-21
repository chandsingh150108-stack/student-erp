package com.studenterp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_scholarships")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentScholarship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scholarship_id", nullable = false)
    private Scholarship scholarship;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String applicationStatus = "APPLIED";

    @Column(length = 20)
    private String awardStatus;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime applicationDate = LocalDateTime.now();

    private LocalDateTime awardDate;
}
