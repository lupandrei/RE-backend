package com.example.rebackend.controller;

import com.example.rebackend.dto.InternshipCreateDTO;
import com.example.rebackend.dto.InternshipDTO;
import com.example.rebackend.service.InternshipService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor

public class InternshipController {

    private InternshipService internshipService;

    @GetMapping("/filter")
    public ResponseEntity<List<InternshipDTO>> filterInternships(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String name) {
        return ResponseEntity.ok(internshipService.filterInternships(department, companyName, name));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addInternship(@RequestBody InternshipCreateDTO internshipCreateDTO) {
        internshipService.addInternship(internshipCreateDTO);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/{internshipId}/apply/{studentId}")
    public ResponseEntity<Void> applyToInternship(@PathVariable Long internshipId, @PathVariable Long studentId) {
        internshipService.applyToInternship(studentId, internshipId);
        return ResponseEntity.status(201).build();
    }
}

