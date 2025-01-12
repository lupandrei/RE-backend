package com.example.rebackend.model;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "companies")
@Getter
public class CompanyAccount implements UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String description;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }
}