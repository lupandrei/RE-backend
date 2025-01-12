package com.example.rebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudentDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String description;
}
