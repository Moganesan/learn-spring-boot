package com.schoolmanagementsystem.schoolmanagementsystem;

import com.schoolmanagementsystem.schoolmanagementsystem.exceptions.ResourceNotFoundException;
import com.schoolmanagementsystem.schoolmanagementsystem.models.Student;
import com.schoolmanagementsystem.schoolmanagementsystem.repository.StudentRepository;
import com.schoolmanagementsystem.schoolmanagementsystem.services.StudentService;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class ServiceLevelTesting {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void addNewStudentTest() {
        Student student = new Student();
        student.setName("Sample Student");
        student.setAge(90);

        Student newStudent = studentService.addNewStudent(student);

        Assertions.assertNotNull(newStudent);
        Assertions.assertEquals(student,newStudent);
    }

    @Test
    public void getAllStudentsTest(){
        Integer studentCount = 3;
        List<Student> studentsList = studentService.getAllStudents();
        Assertions.assertTrue(studentsList.size()>=1);
        Assertions.assertNotNull(studentsList);
        Assertions.assertEquals(studentsList.size(),studentCount);
    }

    @Test
    public void getStudentWithIdTest(){
        UUID studentId = UUID.fromString("1726ae75-dfe4-46e7-a3c1-bd608a279650");
        Student student = studentService.getStudentWithId(studentId);
        Assertions.assertNotNull(student);
        Assertions.assertEquals(student.getId(),studentId);
    }

    @Test
    public void updateStudentWithIdTest(){
        UUID studentId = UUID.fromString("1726ae75-dfe4-46e7-a3c1-bd608a279650");
        Student targetStudent = studentRepository.findById(studentId).orElseThrow();
        Integer updateAge = 80;
        targetStudent.setAge(updateAge);
        studentService.updateStudentWithId(studentId,targetStudent);

        Student updatedStudent = studentRepository.findById(studentId).orElseThrow();

        Assertions.assertEquals(updatedStudent.getAge(),updateAge);
    }

    @Test
    public void deleteStudentWithIdTest(){
        UUID studentId = UUID.fromString("6eca6833-e143-4165-b721-74b5ea79aa32");

        // Delete the student
        studentService.deleteStudentWithId(studentId);

        // Verify that the student record is deleted
        assertNull(studentRepository.findById(studentId).orElse(null));
    }
}
