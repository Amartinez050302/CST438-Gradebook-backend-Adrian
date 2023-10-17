package com.cst438.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cst438.domain.FinalGradeDTO;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Enrollment;

@Service
@ConditionalOnProperty(prefix = "registration", name = "service", havingValue = "rest")
@RestController
public class RegistrationServiceREST implements RegistrationService {

    RestTemplate restTemplate = new RestTemplate();
    
    @Value("${registration.url}") 
    String registration_url;
    
    public RegistrationServiceREST() {
        System.out.println("REST registration service ");
    }
    
    @Override
    public void sendFinalGrades(int course_id, FinalGradeDTO[] grades) { 
        // Construct the URL for sending the final grades
        String finalGradesUrl = registration_url + "/course/" + course_id + "/finalgrades";
        
        // Use restTemplate to send final grades to the registration service
        restTemplate.put(finalGradesUrl, grades);
    }
    
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    EnrollmentRepository enrollmentRepository;

    
    @PostMapping("/enrollment")
    @Transactional
    public EnrollmentDTO addEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
        // Receive message from registration service to enroll a student into a course.
        System.out.println("GradeBook addEnrollment " + enrollmentDTO);
        
        Course course = courseRepository.findByCourse_id(enrollmentDTO.course_id);
        if(course == null) {
            throw new RuntimeException("Course not found with id " + enrollmentDTO.course_id);
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudentEmail(enrollmentDTO.getStudentEmail());
        enrollment.setStudentName(enrollmentDTO.getStudentName());
        enrollment.setGrade(enrollmentDTO.grade);

        enrollmentRepository.save(enrollment);

        return enrollmentDTO;        
    }
}