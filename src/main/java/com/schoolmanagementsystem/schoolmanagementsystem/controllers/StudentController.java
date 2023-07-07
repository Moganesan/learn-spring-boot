package com.schoolmanagementsystem.schoolmanagementsystem.controllers;

import com.schoolmanagementsystem.schoolmanagementsystem.models.Student;
import com.schoolmanagementsystem.schoolmanagementsystem.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @PostMapping(value = "/addNewStudent")
    public ResponseEntity<?> addNewUser(@RequestBody Student student){
        Student savedStudent =  studentService.addNewStudent(student);
        return ResponseEntity.ok("New Student Added To DB.");
    }

    @GetMapping
    public ResponseEntity<?> getAllUser(){
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students,HttpStatus.OK);
    }

    @GetMapping(value = "/{studentId}")
    public ResponseEntity<?> getUser(@PathVariable UUID studentId){
        Student student = studentService.getStudentWithId(studentId);
        return ResponseEntity.ok(student);
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<?> updateUser(@PathVariable UUID studentId,@RequestBody Student updatedStudent){
         studentService.updateStudentWithId(studentId,updatedStudent);
            return ResponseEntity.ok("Student Details Updated");
    }


    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID studentId){
    studentService.deleteStudentWithId(studentId);
        return ResponseEntity.ok("Student Deleted Successfully.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Handle the exception and return an appropriate response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred"+ex);
    }
}
