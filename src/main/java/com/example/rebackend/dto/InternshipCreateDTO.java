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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
