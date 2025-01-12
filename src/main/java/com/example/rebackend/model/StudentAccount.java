package com.example.rebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
public class StudentAccount implements UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private String description;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
