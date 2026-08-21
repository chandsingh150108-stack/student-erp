package com.studenterp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "results")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @Column(nullable = false)
    private Double cgpa;

    @Column(nullable = false)
    private Double sgpa;

    @Column(nullable = false)
    private Integer totalCredits;

    @Column(nullable = false)
    private Integer earnedCredits;
}
