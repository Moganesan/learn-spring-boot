package com.schoolmanagementsystem.schoolmanagementsystem.services;

import com.schoolmanagementsystem.schoolmanagementsystem.exceptions.ResourceNotFoundException;
import com.schoolmanagementsystem.schoolmanagementsystem.models.Student;
import com.schoolmanagementsystem.schoolmanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
     @Autowired
     private StudentRepository studentRepository;

     public List<Student> getAllStudents(){
          return studentRepository.findAll();
     }

     public Student addNewStudent(Student student){
          return studentRepository.save(student);
     }

     public Student getStudentWithId(UUID studentId){
          return studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
     }

     public void updateStudentWithId(UUID studentId, Student updatedStudent){
          Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
          student.setName(updatedStudent.getName());
          student.setAge(updatedStudent.getAge());
          student.setRollNo(updatedStudent.getRollNo());
          studentRepository.save(student);
     }
     public void deleteStudentWithId(UUID studentId) {
          studentRepository.deleteById(studentId);
     }
}
