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
    
// Class names abide by correct coding conventions: class name starts with an uppercase
// Method and variable names abide by correct coding conventions: camel case conventions
// Has all the required methods
public class AssignmentController {

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    CourseRepository courseRepository;


    // POST method's logic and functionality are correct: create a new assignmentDTO object and set the values.
    // Called courseRepository and stores AssignmentDTO in courseRepo database
    // Provides exception if adding a NULL value to the course
    // Returns correct object 
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

    // GET method's logic is correct and the code readability is good.
    // Code comprehensibility is good: can understand method return value and arguments via @PathVariable is being passed from the URL
    // Code comments are present and provide clarification on code logic
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

    // Provides PUT method that passes an ID and updates the assignment with relevant fields
    // Exception is provided if the assignment object from assignmentRepository is of NULL value
    // Returns AssignmentDTO object with updated values.
    // Throws exception with HTTPStatus is assignment ID passed from URL cannot be found and/or is of NULL value
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

    // Provides DELETE method that passes ID from URL and searches assignment repository for the given ID
    // Throws exception with HTTPstatus if ID is not found in the assignment repository
    // Deletes given assignment in assignment repository
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
