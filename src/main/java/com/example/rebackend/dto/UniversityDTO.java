package com.example.rebackend.dto;

public class UniversityDTO {
    private Long id;
    private String name;
    private Integer year;
    private String specialization;

    public UniversityDTO(Long id, String name, Integer year, String specialization) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.specialization = specialization;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    @Override
    public String toString() {
        return name;
    }
}
