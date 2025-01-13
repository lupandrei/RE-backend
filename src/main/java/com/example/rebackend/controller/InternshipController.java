package com.example.rebackend.controller;

import com.example.rebackend.dto.InternshipCreateDTO;
import com.example.rebackend.dto.InternshipDTO;
import com.example.rebackend.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/internships")
@RequiredArgsConstructor

public class InternshipController {

    private final InternshipService internshipService;

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

    @PostMapping("/{internshipId}/save/{studentId}")
    public ResponseEntity<Void> saveInternshipForStudent(@PathVariable Long internshipId, @PathVariable Long studentId) {
        internshipService.saveInternshipForStudent(studentId, internshipId);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/status/{studentId}/{status}")
    public ResponseEntity<List<InternshipDTO>> getInternshipsByStatus(@PathVariable Long studentId, @PathVariable(required = false) String status) {
        return ResponseEntity.ok(internshipService.getInternshipsByStatus(studentId, status));
    }

    @GetMapping("/saved/{studentId}")
    public ResponseEntity<List<InternshipDTO>> getSavedInternships(@PathVariable Long studentId) {
        return ResponseEntity.ok(internshipService.getSavedInternships(studentId));
    }
}

