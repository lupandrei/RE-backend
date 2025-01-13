package com.example.rebackend.repository;

import com.example.rebackend.model.StudentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAccountRepository extends JpaRepository<StudentAccount, Long> {

    StudentAccount findByUsername(String username);
}
