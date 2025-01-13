package com.example.rebackend.repository;

import com.example.rebackend.model.StudentInternship;
import com.example.rebackend.model.StudentInternshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentInternshipRepository extends JpaRepository<StudentInternship, Long> {

    List<StudentInternship> findByStudentIdAndStatus(Long studentId, StudentInternshipStatus status);

    List<StudentInternship> findByStudentIdAndSaved(Long studentId, Boolean saved);

    List<StudentInternship> findByStudentId(Long studentId);
}
