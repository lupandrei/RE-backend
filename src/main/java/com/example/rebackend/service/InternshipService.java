package com.example.rebackend.service;

import com.example.rebackend.dto.InternshipCreateDTO;
import com.example.rebackend.dto.InternshipDTO;
import com.example.rebackend.model.*;
import com.example.rebackend.repository.CompanyAccountRepository;
import com.example.rebackend.repository.InternshipRepository;
import com.example.rebackend.repository.StudentAccountRepository;
import com.example.rebackend.repository.StudentInternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class InternshipService {

    private final InternshipRepository internshipRepository;
    private final CompanyAccountRepository companyRepository;
    private final StudentAccountRepository studentRepository;
    private final StudentInternshipRepository studentInternshipRepository;

    public List<InternshipDTO> filterInternships(String department, String companyName, String name) {
        return internshipRepository.findAll(filterByCriteria(department, companyName, name))
                .stream()
                .map(internship -> new InternshipDTO(
                        internship.getId(),
                        internship.getName(),
                        internship.getDepartment(),
                        internship.getDescription(),
                        internship.getCompany().getName()
                ))
                .collect(Collectors.toList());

    }

    private static Specification<Internship> filterByCriteria(String department, String companyName, String name) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            if (department != null && !department.isEmpty()) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("department"), department));
            }

            if (companyName != null && !companyName.isEmpty()) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("company").get("name"), companyName));
            }

            if (name != null && !name.isEmpty()) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            return predicates;
        };
    }

    public void addInternship(InternshipCreateDTO internshipCreateDTO) {
        CompanyAccount company = companyRepository.findById(internshipCreateDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        Internship internship = new Internship();
        internship.setName(internshipCreateDTO.getName());
        internship.setDepartment(internshipCreateDTO.getDepartment());
        internship.setDescription(internshipCreateDTO.getDescription());
        internship.setCompany(company);

        internshipRepository.save(internship);
    }

    public void applyToInternship(Long studentId, Long internshipId) {
        StudentAccount student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new IllegalArgumentException("Internship not found"));

        StudentInternship studentInternship = new StudentInternship();
        studentInternship.setStudent(student);
        studentInternship.setInternship(internship);
        studentInternship.setStatus(StudentInternshipStatus.APPLIED);

        studentInternshipRepository.save(studentInternship);
    }
}
