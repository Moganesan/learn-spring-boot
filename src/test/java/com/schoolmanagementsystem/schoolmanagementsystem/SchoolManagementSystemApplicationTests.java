package com.schoolmanagementsystem.schoolmanagementsystem;

import com.schoolmanagementsystem.schoolmanagementsystem.controllers.StudentController;
import com.schoolmanagementsystem.schoolmanagementsystem.models.Student;
import com.schoolmanagementsystem.schoolmanagementsystem.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

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
	RestTemplate restTemplate = new RestTemplate();


	@Test
	public void addNewStudentTest() {
		Student student = new Student();
		student.setName("Sample Student");
		student.setAge(90);
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNb2dhbmVzYW4iLCJpYXQiOjE2ODkxNDcyNzksImV4cCI6MTY4OTIzMzY3OX0.EMyXO_73Uhl6wd8z4GbbAzSsIMk6rHOTYePkMUY1rRZ27rryUVNOIR6wj3pFZC3HzqFLscVISygTkaxObnCvQg");
		HttpEntity<Student> requestEntity = new HttpEntity<>(student, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"http://localhost:4040/student/addNewStudent",
				HttpMethod.POST,
				requestEntity,
				String.class
		);

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertEquals("New Student Added To DB.", responseEntity.getBody());
	}

	@Test
	public void getAllStudentsTest(){
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNb2dhbmVzYW4iLCJpYXQiOjE2ODkxNDcyNzksImV4cCI6MTY4OTIzMzY3OX0.EMyXO_73Uhl6wd8z4GbbAzSsIMk6rHOTYePkMUY1rRZ27rryUVNOIR6wj3pFZC3HzqFLscVISygTkaxObnCvQg");
		HttpEntity<Student> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"http://localhost:4040/student",
				HttpMethod.GET,
				requestEntity,
				String.class
		);

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void getStudentWithIdTest(){
		UUID studentId = UUID.fromString("17f439d9-2f8a-400a-b8b2-2315087dcbca");
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNb2dhbmVzYW4iLCJpYXQiOjE2ODkxNDcyNzksImV4cCI6MTY4OTIzMzY3OX0.EMyXO_73Uhl6wd8z4GbbAzSsIMk6rHOTYePkMUY1rRZ27rryUVNOIR6wj3pFZC3HzqFLscVISygTkaxObnCvQg");
		HttpEntity<Student> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"http://localhost:4040/student/"+studentId,
				HttpMethod.GET,
				requestEntity,
				String.class
		);

		Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
	}

	@Test
	public void updateStudentWithIdTest() {
		UUID studentId = UUID.fromString("17f439d9-2f8a-400a-b8b2-2315087dcbca");
		Student targetStudent = studentRepository.findById(studentId).orElseThrow();
		Integer updateAge = 90;
		targetStudent.setAge(updateAge);

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNb2dhbmVzYW4iLCJpYXQiOjE2ODkxNDcyNzksImV4cCI6MTY4OTIzMzY3OX0.EMyXO_73Uhl6wd8z4GbbAzSsIMk6rHOTYePkMUY1rRZ27rryUVNOIR6wj3pFZC3HzqFLscVISygTkaxObnCvQg");

		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

		HttpEntity<Student> requestEntity = new HttpEntity<>(targetStudent, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"http://localhost:4040/student/" + studentId,
				HttpMethod.PATCH,
				requestEntity,
				String.class
		);
		Student updatedStudent = studentRepository.findById(studentId).orElseThrow();

		Assertions.assertEquals(updatedStudent.getAge(), updateAge);
		Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}


	@Test
	public void deleteStudentWithIdTest(){
		UUID studentId = UUID.fromString("1e66edf8-851b-4af7-99b7-4834aec9929d");

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNb2dhbmVzYW4iLCJpYXQiOjE2ODkxNDcyNzksImV4cCI6MTY4OTIzMzY3OX0.EMyXO_73Uhl6wd8z4GbbAzSsIMk6rHOTYePkMUY1rRZ27rryUVNOIR6wj3pFZC3HzqFLscVISygTkaxObnCvQg");

		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

		HttpEntity<Student> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"http://localhost:4040/student/" + studentId,
				HttpMethod.DELETE,
				requestEntity,
				String.class
		);

		Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
	}
}
