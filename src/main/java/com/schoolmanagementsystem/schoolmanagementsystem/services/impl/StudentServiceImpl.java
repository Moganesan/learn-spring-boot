package com.schoolmanagementsystem.schoolmanagementsystem.services.impl;

import com.schoolmanagementsystem.schoolmanagementsystem.exceptions.ResourceNotFoundException;
import com.schoolmanagementsystem.schoolmanagementsystem.exceptions.ValidationException;
import com.schoolmanagementsystem.schoolmanagementsystem.models.Student;
import com.schoolmanagementsystem.schoolmanagementsystem.repository.StudentRepository;
import com.schoolmanagementsystem.schoolmanagementsystem.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {
     @Autowired
     private StudentRepository studentRepository;

     private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

     @Override
     public List<Student> getAllStudents(String traceId) throws ValidationException {
          logger.info("{}: Function start: StudentServiceImpl.getAllStudents()",traceId);
          List<Student> students = studentRepository.findAll();
          logger.info("{}: Function end: StudentServiceImpl.getAllStudents()",traceId);
          return students;
     }

     @Override
     public Student addNewStudent(String traceId,Student student) throws ValidationException {
          logger.info("{}: Function start: StudentServiceImpl.addNewStudent()",traceId);
          Student newStudent = studentRepository.save(student);
          logger.info("{}: Function end: StudentServiceImpl.addNewStudent()",traceId);
          return  newStudent;
     }

     @Override
     public Student getStudentWithId(String traceId,UUID studentId) throws ValidationException {
          logger.info("{}: Function start: StudentServiceImpl.getStudentWithId()",traceId);
          Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
          logger.info("{}: Function end: StudentServiceImpl.getStudentWithId()",traceId);
          return student;
     }

     @Override
     public void updateStudentWithId(String traceId,UUID studentId, Student updatedStudent) throws ValidationException {
          logger.info("{}: Function start: StudentServiceImpl.updateStudentWithId()",traceId);
          Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
          student.setName(updatedStudent.getName());
          student.setAge(updatedStudent.getAge());
          student.setRollNo(updatedStudent.getRollNo());
          studentRepository.save(student);
          logger.info("{}: Function end: StudentServiceImpl.updateStudentWithId()",traceId);
     }

     @Override
     public void deleteStudentWithId(String traceId,UUID studentId) throws ValidationException {
          logger.info("{}: Function start: StudentServiceImpl.deleteStudentWithId()",traceId);
          studentRepository.deleteById(studentId);
          logger.info("{}: Function end: StudentServiceImpl.deleteStudentWithId()",traceId);
     }
}
