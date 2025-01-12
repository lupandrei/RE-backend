package com.example.rebackend.controller;

import com.example.rebackend.dto.StudentDTO;
import com.example.rebackend.dto.UpdateStudentDTO;
import com.example.rebackend.service.StudentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private StudentAccountService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO studentDTO = studentService.getStudentById(id);
        if (studentDTO != null) {
            return ResponseEntity.ok(studentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<Void> updateStudentProfile(@PathVariable Long studentId, @RequestBody UpdateStudentDTO updatedStudentDTO) {
        studentService.updateStudentProfile(studentId, updatedStudentDTO);
        return ResponseEntity.ok().build();
    }
}
