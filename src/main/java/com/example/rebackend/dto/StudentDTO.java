package com.example.rebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class StudentDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String description;
    private List<ProjectDTO> projects;
    private List<SkillDTO> skills;
    private List<UniversityDTO> universityDTOS;
}
