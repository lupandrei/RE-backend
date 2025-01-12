package com.example.rebackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternshipDTO {
    private Long id;
    private String name;
    private String department;
    private String description;
    private String companyName;

    public InternshipDTO(Long id, String name, String department, String description, String companyName) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.description = description;
        this.companyName = companyName;
    }
}
