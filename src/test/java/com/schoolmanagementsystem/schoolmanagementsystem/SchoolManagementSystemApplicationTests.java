package com.schoolmanagementsystem.schoolmanagementsystem;

import com.fasterxml.jackson.core.JsonFactory;
import com.schoolmanagementsystem.schoolmanagementsystem.controllers.StudentController;
import com.schoolmanagementsystem.schoolmanagementsystem.models.Student;
import com.schoolmanagementsystem.schoolmanagementsystem.repository.StudentRepository;
import com.schoolmanagementsystem.schoolmanagementsystem.services.StudentService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.HeaderTransformer;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class SchoolManagementSystemApplicationTests {

	@Autowired
	private StudentController studentController;

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

		ResponseEntity<?> response = studentController.addNewUser(student);

		Assertions.assertEquals(response.getStatusCode(),HttpStatus.OK);
		Assertions.assertEquals(response.getBody(),"New Student Added To DB.");
	}

	@Test
	public void getAllStudentsTest(){
		ResponseEntity<?> response = studentController.getAllUser();
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void getStudentWithIdTest(){
		UUID studentId = UUID.fromString("bc833158-dd61-4746-a772-90a2a2085ed9");
		ResponseEntity<?> response = studentController.getUser(studentId);
		Assertions.assertEquals(response.getStatusCode(),HttpStatus.OK);
	}

	@Test
	public void updateStudentWithIdTest(){
		UUID studentId = UUID.fromString("bc833158-dd61-4746-a772-90a2a2085ed9");
		Student targetStudent = studentRepository.findById(studentId).orElseThrow();
		Integer updateAge = 90;
		targetStudent.setAge(updateAge);
		ResponseEntity<?> response = studentController.updateUser(studentId,targetStudent);

		Assertions.assertEquals(response.getStatusCode(),HttpStatus.OK);
	}

	@Test
	public void deleteStudentWithIdTest(){
		UUID studentId = UUID.fromString("bc833158-dd61-4746-a772-90a2a2085ed9");
		ResponseEntity<?> response = studentController.deleteUser(studentId);

		Assertions.assertEquals(response.getStatusCode(),HttpStatus.OK);
	}
}
