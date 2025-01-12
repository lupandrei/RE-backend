package com.example.rebackend.dto;

import lombok.Data;

@Data
public class UpdateStudentDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String description;
    private Long universityId;
}
