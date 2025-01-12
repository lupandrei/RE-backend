package com.example.rebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "internships")
@Getter
@Setter
public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String department;

    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyAccount company;
}
