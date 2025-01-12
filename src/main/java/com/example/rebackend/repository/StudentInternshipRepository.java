package com.example.rebackend.repository;

import com.example.rebackend.model.StudentInternship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInternshipRepository extends JpaRepository<StudentInternship, Long> {
}
