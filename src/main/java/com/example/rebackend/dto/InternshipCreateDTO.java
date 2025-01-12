package com.example.rebackend.dto;

import lombok.Data;

@Data
public class InternshipCreateDTO {
    private String name;
    private String department;
    private String description;
    private Long companyId;

    public InternshipCreateDTO() {}

    public InternshipCreateDTO(String name, String department, String description, Long companyId) {
        this.name = name;
        this.department = department;
        this.description = description;
        this.companyId = companyId;
    }
}
