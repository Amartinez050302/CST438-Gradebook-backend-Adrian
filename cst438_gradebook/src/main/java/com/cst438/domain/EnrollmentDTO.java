package com.cst438.domain;

public class EnrollmentDTO {

    private int id;
    private String studentEmail;
    private String studentName;
    private int courseId;
    public Object grade;
    public Integer course_id;

    // Default constructor
    public EnrollmentDTO() {}

    // Constructor using record's fields
    public EnrollmentDTO(int id, String studentEmail, String studentName, int courseId) {
        this.id = id;
        this.studentEmail = studentEmail;
        this.studentName = studentName;
        this.courseId = courseId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Object getGrade() {
        return grade;
    }

    public void setGrade(Object grade) {
        this.grade = grade;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }
}
