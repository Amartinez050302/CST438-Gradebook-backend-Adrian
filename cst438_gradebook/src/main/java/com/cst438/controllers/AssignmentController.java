package com.cst438.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentDTO;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;

import java.util.List;

@RestController
@CrossOrigin
public class AssignmentController {

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    CourseRepository courseRepository;

    

    @PostMapping("/assignment")
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentDTO createAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        // Create a new assignment and save it to the database
        Assignment assignment = new Assignment();
        assignment.setName(assignmentDTO.getName());
        assignment.setDueDate(java.sql.Date.valueOf(assignmentDTO.dueDate()));

        // Retrieve the course based on course_id from the DTO
        Course course = courseRepository.findById(assignmentDTO.courseId()).orElse(null);

        if (course == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid course ID.");
        }

        assignment.setCourse(course);
        assignmentRepository.save(assignment);

        // Return the created assignment
        return new AssignmentDTO(assignment.getId(), assignment.getName(), assignment.getDueDate().toString(),
                assignment.getCourse().getTitle(), assignment.getCourse().getCourse_id());
    }

    @GetMapping("/assignment/{id}")
    public AssignmentDTO getAssignmentById(@PathVariable("id") Integer assignmentId) {
        // Retrieve the assignment by its ID
        Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);

        if (assignment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found.");
        }

        // Return the assignment as DTO
        return new AssignmentDTO(assignment.getId(), assignment.getName(), assignment.getDueDate().toString(),
                assignment.getCourse().getTitle(), assignment.getCourse().getCourse_id());
    }

    @PutMapping("/assignment/{id}")
    public AssignmentDTO updateAssignment(@PathVariable("id") Integer assignmentId,
            @RequestBody AssignmentDTO assignmentDTO) {
        // Retrieve the assignment by its ID
        Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);

        if (assignment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found.");
        }

        // Update the assignment with the new values from the DTO
        assignment.setName(assignmentDTO.getName());
        assignment.setDueDate(java.sql.Date.valueOf(assignmentDTO.dueDate()));

        // Retrieve the course based on course_id from the DTO
        Course course = courseRepository.findById(assignmentDTO.courseId()).orElse(null);

        if (course == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid course ID.");
        }

        assignment.setCourse(course);
        assignmentRepository.save(assignment);

        // Return the updated assignment as DTO
        return new AssignmentDTO(assignment.getId(), assignment.getName(), assignment.getDueDate().toString(),
                assignment.getCourse().getTitle(), assignment.getCourse().getCourse_id());
    }

    @DeleteMapping("/assignment/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssignment(@PathVariable("id") Integer assignmentId) {
        // Retrieve the assignment by its ID
        Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);

        if (assignment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found.");
        }

        // Delete the assignment
        assignmentRepository.delete(assignment);
    }
}