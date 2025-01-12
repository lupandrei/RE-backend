package com.example.rebackend.service;

import com.example.rebackend.dto.StudentDTO;
import com.example.rebackend.dto.UpdateStudentDTO;
import com.example.rebackend.model.StudentAccount;
import com.example.rebackend.model.University;
import com.example.rebackend.repository.StudentAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentAccountService {

    private final StudentAccountRepository studentRepository;

    public StudentDTO getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(student -> new StudentDTO(
                        student.getId(),
                        student.getUsername(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getDescription()
                ))
                .orElse(null);
    }

    public void updateStudentProfile(Long studentId, UpdateStudentDTO updatedStudentDTO) {
        StudentAccount existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        existingStudent.setUsername(updatedStudentDTO.getUsername());
        existingStudent.setFirstName(updatedStudentDTO.getFirstName());
        existingStudent.setLastName(updatedStudentDTO.getLastName());
        existingStudent.setDescription(updatedStudentDTO.getDescription());

        if (updatedStudentDTO.getUniversityId() != null) {
            University university = new University();
            university.setId(updatedStudentDTO.getUniversityId());
            existingStudent.setUniversity(university);
        }

        studentRepository.save(existingStudent);
    }
}
