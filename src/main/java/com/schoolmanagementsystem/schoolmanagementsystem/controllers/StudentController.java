package com.schoolmanagementsystem.schoolmanagementsystem.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolmanagementsystem.schoolmanagementsystem.exceptions.ValidationException;
import com.schoolmanagementsystem.schoolmanagementsystem.models.Student;
import com.schoolmanagementsystem.schoolmanagementsystem.models.response.ResponseMessage;
import com.schoolmanagementsystem.schoolmanagementsystem.models.response.ResponseStatus;
import com.schoolmanagementsystem.schoolmanagementsystem.services.StudentService;
import com.schoolmanagementsystem.schoolmanagementsystem.services.impl.StudentServiceImpl;
import com.schoolmanagementsystem.schoolmanagementsystem.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * API to add create new student
     * @param student
     * @return
     * @throws ValidationException
     * @throws JsonProcessingException
     * @return 201 created success messages
     */
    @PostMapping(value = "/")
    public ResponseEntity<?> addNewUser(@RequestBody Student student) throws ValidationException, JsonProcessingException {
        String traceId = Utils.getTrackingId();
        String studentJSON =  objectMapper.writeValueAsString(student);
        logger.info("{}: Post Request to create student, Student: {}",traceId,student.getName());
        Student savedStudent =  studentService.addNewStudent(traceId,student);
        return new ResponseEntity<>(new ResponseMessage(201, ResponseStatus.Successful,"Student created successfully"), HttpStatus.CREATED);
    }

    /**
     * API to get all students
     * @return List of students
     * @throws ValidationException
     */
    @GetMapping
    public ResponseEntity<?> getAllUser() throws ValidationException {
        String traceId = Utils.getTrackingId();
        logger.info("{}: Get Request to get all students.",traceId);
        List<Student> students = studentService.getAllStudents(traceId);
        return new ResponseEntity<>(students,HttpStatus.OK);
    }

    /**
     * API to get student
     * @param studentId
     * @return Student object
     * @throws ValidationException
     */
    @GetMapping(value = "/{studentId}")
    public ResponseEntity<?> getUser(@PathVariable UUID studentId) throws ValidationException {
        String traceId = Utils.getTrackingId();
        logger.info("{}: Get Request to get student with id.",traceId);
        Student student = studentService.getStudentWithId(traceId,studentId);
        return ResponseEntity.ok(student);
    }

    /**
     * API to update student details
     * @param studentId
     * @param updatedStudent
     * @return 201 success message
     * @throws ValidationException
     * @throws JsonProcessingException
     */
    @PatchMapping("/{studentId}")
    public ResponseEntity<?> updateUser(@PathVariable UUID studentId,@RequestBody Student updatedStudent) throws ValidationException, JsonProcessingException {
        String traceId = Utils.getTrackingId();
        String studentDetailsJSON = objectMapper.writeValueAsString(updatedStudent);
        logger.info("{}: PATCH Request to update student details: {} with studentId: {} .",traceId,studentDetailsJSON,studentId);
        studentService.updateStudentWithId(traceId,studentId,updatedStudent);
        return new ResponseEntity<>(new ResponseMessage(201, ResponseStatus.Successful,"Student updated successfully"), HttpStatus.CREATED);
    }


    /**
     * Function to delete a student
     * @param studentId
     * @return 200 delete message
     * @throws ValidationException
     */
    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID studentId) throws ValidationException {
        String traceId = Utils.getTrackingId();
        logger.info("{}: DELETE Request to delete student details from DB.",traceId);
        studentService.deleteStudentWithId(traceId,studentId);
        return ResponseEntity.ok("Student Deleted Successfully.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Handle the exception and return an appropriate response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred"+ex);
    }
}
