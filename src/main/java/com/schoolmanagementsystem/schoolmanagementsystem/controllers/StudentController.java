package com.schoolmanagementsystem.schoolmanagementsystem.controllers;

import com.schoolmanagementsystem.schoolmanagementsystem.models.Student;
import com.schoolmanagementsystem.schoolmanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value = "/student")
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    @PostMapping(value = "/addNewStudent")
    public ResponseEntity<?> addNewUser(@RequestBody Student student){
        Student savedStudent =  studentRepository.save(student);
        return ResponseEntity.ok("New Student Added To DB.");
    }

    @GetMapping(value = "/{studentId}")
    public ResponseEntity<?> getUser(@PathVariable Long studentId){
        Optional<Student> student = studentRepository.findById(studentId);
        return ResponseEntity.ok(student);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Handle the exception and return an appropriate response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred"+ex);
    }
}
