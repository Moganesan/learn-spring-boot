package com.schoolmanagementsystem.schoolmanagementsystem;

import com.schoolmanagementsystem.schoolmanagementsystem.exceptions.ValidationException;
import com.schoolmanagementsystem.schoolmanagementsystem.models.Student;
import com.schoolmanagementsystem.schoolmanagementsystem.repository.StudentRepository;
import com.schoolmanagementsystem.schoolmanagementsystem.services.StudentService;
import com.schoolmanagementsystem.schoolmanagementsystem.services.impl.StudentServiceImpl;
import com.schoolmanagementsystem.schoolmanagementsystem.utils.Utils;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void addNewStudentTest() throws ValidationException {
        Student student = new Student();
        student.setName("Sample Student");
        student.setAge(90);
        String tranceId = Utils.getTrackingId();

        Student newStudent = studentService.addNewStudent(tranceId,student);

        Assertions.assertNotNull(newStudent);
        Assertions.assertEquals(student,newStudent);
    }

    @Test
    public void getAllStudentsTest() throws ValidationException {
        Integer studentCount = 3;
        String tranceId = Utils.getTrackingId();
        List<Student> studentsList = studentService.getAllStudents(tranceId);
        Assertions.assertTrue(studentsList.size()>=1);
        Assertions.assertNotNull(studentsList);
        Assertions.assertEquals(studentsList.size(),studentCount);
    }

    @Test
    public void getStudentWithIdTest() throws ValidationException {
        UUID studentId = UUID.fromString("1726ae75-dfe4-46e7-a3c1-bd608a279650");
        String tranceId = Utils.getTrackingId();
        Student student = studentService.getStudentWithId(tranceId,studentId);
        Assertions.assertNotNull(student);
        Assertions.assertEquals(student.getId(),studentId);
    }

    @Test
    public void updateStudentWithIdTest() throws ValidationException {
        UUID studentId = UUID.fromString("1726ae75-dfe4-46e7-a3c1-bd608a279650");
        Student targetStudent = studentRepository.findById(studentId).orElseThrow();
        Integer updateAge = 80;
        targetStudent.setAge(updateAge);
        String traceId = Utils.getTrackingId();
        studentService.updateStudentWithId(traceId,studentId,targetStudent);

        Student updatedStudent = studentRepository.findById(studentId).orElseThrow();

        Assertions.assertEquals(updatedStudent.getAge(),updateAge);
    }

    @Test
    public void deleteStudentWithIdTest() throws ValidationException {
        UUID studentId = UUID.fromString("6eca6833-e143-4165-b721-74b5ea79aa32");
        String tranceId = Utils.getTrackingId();
        // Delete the student
        studentService.deleteStudentWithId(tranceId,studentId);

        // Verify that the student record is deleted
        assertNull(studentRepository.findById(studentId).orElse(null));
    }
}
