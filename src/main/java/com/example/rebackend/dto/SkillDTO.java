package com.example.rebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SkillDTO {
    private Long id;
    private String name;
    private int level;
}
