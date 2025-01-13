package com.example.rebackend.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "projects")
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentAccount student;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public StudentAccount getStudent() {
        return student;
    }
}
