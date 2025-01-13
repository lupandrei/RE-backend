package com.example.rebackend.service;

import com.example.rebackend.dto.*;
import com.example.rebackend.client.FreeAIClient;
import com.example.rebackend.dto.ProjectDTO;
import com.example.rebackend.dto.SkillsDTO;
import com.example.rebackend.dto.StudentDTO;
import com.example.rebackend.dto.UpdateStudentDTO;
import com.example.rebackend.model.Project;
import com.example.rebackend.model.Skill;
import com.example.rebackend.model.StudentAccount;
import com.example.rebackend.model.University;
import com.example.rebackend.repository.ProjectRepository;
import com.example.rebackend.repository.SkillRepository;
import com.example.rebackend.repository.StudentAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentAccountService {

    private final StudentAccountRepository studentRepository;
    private final ProjectRepository projectRepository;
    private final FreeAIClient descriptionClient;
    private final SkillRepository skillRepository;

    public StudentDTO getStudentById(Long id) {
        return studentRepository.findById(id).map(student -> {
            List<ProjectDTO> projects = student.getProjects().stream()
                    .map(project -> new ProjectDTO(project.getId(), project.getName(), project.getDescription()))
                    .collect(Collectors.toList());

            List<SkillDTO> skills = student.getSkills().stream()
                    .map(skill -> new SkillDTO(skill.getId(), skill.getName(), skill.getLevel()))
                    .collect(Collectors.toList());

            List<UniversityDTO> universities = student.getStudentUniversities().stream()
                    .map(studentUniversity -> new UniversityDTO(
                            studentUniversity.getUniversity().getId(),
                            studentUniversity.getUniversity().getName(),
                            studentUniversity.getYear(),
                            studentUniversity.getSpecialization()))
                    .collect(Collectors.toList());

            return new StudentDTO(
                    student.getId(),
                    student.getUsername(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getDescription(),
                    projects,
                    skills,
                    universities
            );
        }).orElseThrow(() -> new IllegalArgumentException("Student not found"));
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
        }

        studentRepository.save(existingStudent);
    }

    public Long getStudentIdByUsername(String username) {
        StudentAccount byUsername = studentRepository.findByUsername(username);
        if (byUsername != null) {
            return byUsername.getId();
        }
        return null;
    }

    public String generateStudentDescription(Long studentId) throws IOException, InterruptedException {
        StudentAccount existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        var studentSkills = extractSkills(studentId);
        var studentProjects = extractProjects(studentId);

        return descriptionClient.retrieveGeneratedDescription(existingStudent, convertSkillsToDTO(studentSkills), convertProjectstoDTO(studentProjects));
    }

    private List<Skill> extractSkills(Long studentId) {
        List<Skill> skills = skillRepository.findAll();
        return skills.stream()
                .filter(skill -> skill.getStudent().getId() == studentId)
                .toList();

    }

    private List<Project> extractProjects(Long studentId) {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .filter(project -> project.getStudent().getId() == studentId)
                .toList();

    }


    private List<ProjectDTO> convertProjectstoDTO(List<Project> studentProjects) {
        return studentProjects.stream()
                .map(project -> {
                    var projectDTO = new ProjectDTO();
                    projectDTO.setName(project.getName());
                    projectDTO.setDescription(project.getDescription());
                    return projectDTO;
                })
                .toList();
    }

    private List<SkillsDTO> convertSkillsToDTO(List<Skill> studentSkills) {
        return studentSkills.stream()
                .map(skill -> {
                    var skillsDTO = new SkillsDTO();
                    skillsDTO.setName(skill.getName());
                    skillsDTO.setLevel(skill.getLevel());
                    return skillsDTO;
                })
                .toList();
    }
}
