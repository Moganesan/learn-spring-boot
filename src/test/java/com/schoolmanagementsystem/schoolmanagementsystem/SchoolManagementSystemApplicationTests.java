package com.schoolmanagementsystem.schoolmanagementsystem;

import com.schoolmanagementsystem.schoolmanagementsystem.controllers.StudentController;
import com.schoolmanagementsystem.schoolmanagementsystem.models.Student;
import com.schoolmanagementsystem.schoolmanagementsystem.repository.StudentRepository;
import com.schoolmanagementsystem.schoolmanagementsystem.services.StudentService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.HeaderTransformer;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@SpringBootTest
class SchoolManagementSystemApplicationTests {

	@Mock
	private StudentService studentService;

	@InjectMocks
	private StudentController studentController;

	@Before("")
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void addNewStudentTest() {
		Student student = new Student();
		student.setName("Sample Student");
		student.setAge(90);

		ResponseEntity<?> response = studentController.addNewUser(student);

		assert response.getStatusCode()	== HttpStatus.OK;
		assert response.getBody().equals("New Student Added To DB.");
	}
}
