package com.example.rebackend.controller;

import com.example.rebackend.dto.StudentDTO;
import com.example.rebackend.dto.UpdateStudentDTO;
import com.example.rebackend.service.StudentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    @Autowired
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

    @GetMapping("/student-id/{username}")
    public ResponseEntity<Long> getStudentIdByUsername(@PathVariable String username) {
        return ResponseEntity.ok(studentService.getStudentIdByUsername(username));
    }

    @PostMapping("/generate-description/{studentId}")
    public ResponseEntity<String> generateStudentDescription(@PathVariable Long studentId) throws IOException, InterruptedException {
        var response = studentService.generateStudentDescription(studentId);
        return ResponseEntity.ok(response);
    }
}
