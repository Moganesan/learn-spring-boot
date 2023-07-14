package com.schoolmanagementsystem.schoolmanagementsystem.services;

import com.schoolmanagementsystem.schoolmanagementsystem.exceptions.ValidationException;
import com.schoolmanagementsystem.schoolmanagementsystem.models.Student;

import java.util.List;
import java.util.UUID;

public interface StudentService {

    /**
     * Function to get all students
     * @return List<Student>
     * @throws ValidationException
     */
    List<Student> getAllStudents(String traceId) throws ValidationException;

    /**
     * Function to adding new student
     * @param student
     * @return student class
     * @throws ValidationException
     */
    Student addNewStudent(String traceId,Student student) throws ValidationException;

    /**
     * Function to get student with id
     * @param studentId
     * @return student class
     * @throws ValidationException
     */
    Student getStudentWithId(String traceId,UUID studentId) throws ValidationException;

    /**
     * Function to update student details
     * @param studentId
     * @param updatedStudent
     * @throws ValidationException
     */
    void updateStudentWithId(String traceId,UUID studentId, Student updatedStudent) throws  ValidationException;

    /**
     * Function to deleting student
     * @param studentId
     * @throws ValidationException
     */
    void deleteStudentWithId(String traceId,UUID studentId) throws  ValidationException;
}
