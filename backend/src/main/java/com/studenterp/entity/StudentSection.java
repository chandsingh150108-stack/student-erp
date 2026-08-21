package com.studenterp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_sections")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;
}
