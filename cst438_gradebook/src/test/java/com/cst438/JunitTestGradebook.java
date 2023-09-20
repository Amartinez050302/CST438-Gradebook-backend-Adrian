package com.cst438;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.GradeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class JunitTestGradebook {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AssignmentGradeRepository assignmentGradeRepository;

    @Test
    public void createAssignmentGradeTest() throws Exception {
        // Create a new assignment grade for a student
    	GradeDTO gradeDTO = new GradeDTO(0, "John Doe", "johndoe@example.com", 85); 

        MockHttpServletResponse response = mvc
            .perform(
                MockMvcRequestBuilders
                    .post("/gradebook")
                    .content(asJsonString(gradeDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn()
            .getResponse();

        // Verify that the assignment grade was created successfully
        assertEquals(201, response.getStatus());

        // Verify that the assignment grade exists in the database
        AssignmentGrade createdGrade = assignmentGradeRepository.findByStudentEmail("johndoe@example.com");
        assertNotNull(createdGrade);
        assertEquals(85, createdGrade.getScore());
    }

    @Test
    public void getAssignmentGradeByIdTest() throws Exception {
        // Retrieve an assignment grade by its ID
        MockHttpServletResponse response = mvc
            .perform(
                MockMvcRequestBuilders
                    .get("/gradebook/{id}", 1)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andReturn()
            .getResponse();

        // Verify that the response status is OK (200)
        assertEquals(200, response.getStatus());

        // Verify the content of the retrieved assignment grade (adjust as per your data model)
        GradeDTO gradeDTO = fromJsonString(response.getContentAsString(), GradeDTO.class);
        // Perform assertions based on your data model
    }

    @Test
    public void updateAssignmentGradeTest() throws Exception {
        // Update an existing assignment grade
        GradeDTO updatedGradeDTO = new GradeDTO(1, "John Doe", "johndoe@example.com", 90);

        MockHttpServletResponse response = mvc
            .perform(
                MockMvcRequestBuilders
                    .put("/gradebook/{id}", 1)
                    .content(asJsonString(updatedGradeDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn()
            .getResponse();

        // Verify that the update was successful
        assertEquals(200, response.getStatus());

        // Verify that the assignment grade was updated in the database
        AssignmentGrade updatedGrade = assignmentGradeRepository.findByStudentEmail("johndoe@example.com");
        assertNotNull(updatedGrade);
        assertEquals(90, updatedGrade.getScore());
    }

    @Test
    public void deleteAssignmentGradeTest() throws Exception {
        // Delete an assignment grade by its ID
        MockHttpServletResponse response = mvc
            .perform(
                MockMvcRequestBuilders
                    .delete("/gradebook/{id}", 1)
            )
            .andReturn()
            .getResponse();

        // Verify that the delete operation was successful
        assertEquals(204, response.getStatus());

        // Verify that the assignment grade is no longer in the database
        AssignmentGrade deletedGrade = assignmentGradeRepository.findByStudentEmail("johndoe@example.com");
        assertNull(deletedGrade);
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T fromJsonString(String str, Class<T> valueType) {
        try {
            return new ObjectMapper().readValue(str, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
