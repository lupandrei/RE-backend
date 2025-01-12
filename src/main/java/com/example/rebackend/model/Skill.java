package com.example.rebackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer level;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentAccount student;
}
