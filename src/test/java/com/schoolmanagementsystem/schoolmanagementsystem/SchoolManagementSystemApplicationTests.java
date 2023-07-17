package com.schoolmanagementsystem.schoolmanagementsystem;

import com.schoolmanagementsystem.schoolmanagementsystem.controllers.StudentController;
import com.schoolmanagementsystem.schoolmanagementsystem.exceptions.ResourceNotFoundException;
import com.schoolmanagementsystem.schoolmanagementsystem.models.Student;
import com.schoolmanagementsystem.schoolmanagementsystem.repository.StudentRepository;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@SpringBootTest
class SchoolManagementSystemApplicationTests {

	String expiredJWT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNb2dhbmVzYW4iLCJpYXQiOjE2ODkxNDcyNzksImV4cCI6MTY4OTIzMzY3OX0.EMyXO_73Uhl6wd8z4GbbAzSsIMk6rHOTYePkMUY1rRZ27rryUVNOIR6wj3pFZC3HzqFLscVISygTkaxObnCvQg";
	String validJWT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNb2dhbmVzYW4iLCJpYXQiOjE2ODk1ODAwNTEsImV4cCI6MTY4OTY2NjQ1MX0.U03tclGFYwOSyX4Cj2tVpVWjbW5CMkz3IzDqnvZxZFk4gAi83QMtyAwlngth6SFvv33djxCZ7JA0TjJv1CoXlA";

	@Autowired
	private StudentController studentController;

	@Autowired
	private StudentRepository studentRepository;


	@Test
	void contextLoads() {
	}
	RestTemplate restTemplate = new RestTemplate();


	//add student should work with jwt token
	@Test
	public void addNewStudentTest() {
		Student student = new Student();
		student.setName("Sample Student");
		student.setAge(90);
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(validJWT);
		HttpEntity<Student> requestEntity = new HttpEntity<>(student, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:4040/student",
				HttpMethod.POST,
				requestEntity,
				String.class
		);

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	//testing addNewStudent with Incorrect Jwt Token format
	@Test
	public void addNewStudentWithInvalidJwt(){
		Student student = new Student();
		student.setName("Sample Student");
		student.setAge(40);
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("sdssdsd");
		try {
			HttpEntity<Student> requestEntity = new HttpEntity<>(student,headers);
			ResponseEntity<String> response = restTemplate.exchange("http://localhost:4040/student",HttpMethod.POST,requestEntity,String.class);
			Assertions.assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
			Assertions.assertEquals(response.getBody(),"Incorrect Jwt Token format");
			String responseBody = response.getBody();
		}catch(HttpClientErrorException.Unauthorized e){
		}
	}

	//checking addNewStudent with expired jwt token
	@Test
	public void addNewStudentWithExpiredJwtToken(){
		Student student = new Student();
		student.setName("Sample Student");
		student.setAge(30);
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(expiredJWT);
		try{
			HttpEntity<Student> requestEntity = new HttpEntity<>(student,headers);
			ResponseEntity<String> response = restTemplate.exchange("http://localhost:4040/student",HttpMethod.POST,requestEntity,String.class);
			Assertions.assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
			Assertions.assertEquals("JWT Token has expired",response.getBody());
			String responseBody = response.getBody();
		}catch (HttpClientErrorException.Unauthorized e){
		}
	}

	//get all students should work with valid jwt token
	@Test
	public void getAllStudentsTest(){
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNb2dhbmVzYW4iLCJpYXQiOjE2ODk1ODAwNTEsImV4cCI6MTY4OTY2NjQ1MX0.U03tclGFYwOSyX4Cj2tVpVWjbW5CMkz3IzDqnvZxZFk4gAi83QMtyAwlngth6SFvv33djxCZ7JA0TjJv1CoXlA");
		HttpEntity<Student> requestEntity = new HttpEntity<>(headers);

			ResponseEntity<String> responseEntity = restTemplate.exchange(
					"http://localhost:4040/student",
					HttpMethod.GET,
					requestEntity,
					String.class
			);
			Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
	}

	//checking getAllStudentsWithInvalidJWT
	@Test
	public void getAllStudentsWithInvalidJWT(){
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("sdmskds");
		HttpEntity<Student> requestEntity = new HttpEntity<>(headers);

		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:4040/student",HttpMethod.GET,requestEntity,String.class);
			Assertions.assertEquals(HttpStatus.UNAUTHORIZED,responseEntity.getStatusCode());
			Assertions.assertEquals("Incorrect Jwt Token format",responseEntity.getBody());
		}catch (HttpClientErrorException e){
		}
	}

	//checking getAllStudentsWithExpiredJWT
	@Test
	public void getAllStudentsWithExpiredJWT(){
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(expiredJWT);
		HttpEntity<Student> requestEntity = new HttpEntity<>(headers);

		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:4040/student",HttpMethod.GET,requestEntity,String.class);
			Assertions.assertEquals(HttpStatus.UNAUTHORIZED,responseEntity.getStatusCode());
			System.out.println("Request Body"+responseEntity.getBody());
			Assertions.assertEquals("JWT Token has expired",responseEntity.getBody());
		}catch (HttpClientErrorException e){
		}
	}

	//checking getStudent With ValidJWT and invalid studentId
	@Test
	public void getStudentWithValidJWT(){
		UUID studentId = UUID.fromString("17f439d9-2f8a-400a-b8b2-2315087dcbca");
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(validJWT);
		HttpEntity<Student> requestEntity = new HttpEntity<>(headers);

		try{
			ResponseEntity<String> responseEntity = restTemplate.exchange(
					"http://localhost:4040/student/"+studentId,
					HttpMethod.GET,
					requestEntity,
					String.class
			);
			Assertions.assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
			Assertions.assertTrue(responseEntity.getBody().contains("Student not found with id"));
		}catch (HttpClientErrorException e){
		}
	}

	//checking getStudent with ValidJwt and valid studentId
	@Test
	public void getStuentWithValidJWTAndValidStudentId(){
		UUID studentId = UUID.fromString("06515eb1-3ff7-41bc-9f49-3de8514ef404");
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(validJWT);
		HttpEntity<Student> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:4040/student/"+studentId,HttpMethod.GET,requestEntity,String.class);
		Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
	}

	//checking getStudent with expired jwt token
	@Test
	public void getStudentWithExpiredJWT(){
		UUID studentId = UUID.fromString("06515eb1-3ff7-41bc-9f49-3de8514ef404");
		HttpHeaders  headers = new HttpHeaders();
		headers.setBearerAuth(expiredJWT);
		HttpEntity<Student> requestEntity = new HttpEntity<>(headers);
		try{
			ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:4040/student"+studentId,HttpMethod.GET,requestEntity,String.class);
			Assertions.assertEquals(HttpStatus.UNAUTHORIZED,responseEntity.getStatusCode());
			Assertions.assertEquals("JWT Token has expired",responseEntity.getBody());
		}catch (HttpClientErrorException e){
		}
	}


	//checking updateStudent with invalid student id
	@Test
	public void updateStudentWithIdTest() {
		UUID studentId = UUID.fromString("17f439d9-2f8a-400a-b8b2-2315087dcbca");
		Student targetStudent = new Student();
		Integer updateAge = 90;
		targetStudent.setAge(updateAge);
		targetStudent.setName("Updated Name");

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(validJWT);

		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

		HttpEntity<Student> requestEntity = new HttpEntity<>(targetStudent, headers);
		try{
			ResponseEntity<String> responseEntity = restTemplate.exchange(
					"http://localhost:4040/student/" + studentId,
					HttpMethod.PATCH,
					requestEntity,
					String.class
			);
			Assertions.assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
			Assertions.assertTrue(responseEntity.getBody().contains("Student not found with id"));
		}catch (HttpClientErrorException e){
		}
	}

	//checking update student with valid student id
	@Test
	public void updateStudentDetailsWithValidId(){
		UUID studentId = UUID.fromString("06515eb1-3ff7-41bc-9f49-3de8514ef404");
		Student targetStudent = new Student();
		Integer updateAge = 90;
		targetStudent.setAge(updateAge);
		targetStudent.setName("Updated Name");

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(validJWT);

		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

		HttpEntity<Student> requestEntity = new HttpEntity<>(targetStudent, headers);
		try{
			ResponseEntity<String> responseEntity = restTemplate.exchange(
					"http://localhost:4040/student/" + studentId,
					HttpMethod.PATCH,
					requestEntity,
					String.class
			);
			Assertions.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
		}catch (HttpClientErrorException e){
		}
	}

	//checking update student with expired jwt
	@Test
	public void updateStudentDetailsWithExpiredJwt(){
		UUID studentId = UUID.fromString("06515eb1-3ff7-41bc-9f49-3de8514ef404");
		Student targetStudent = new Student();
		Integer updateAge = 90;
		targetStudent.setAge(updateAge);
		targetStudent.setName("Updated Name");

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(expiredJWT);

		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

		HttpEntity<Student> requestEntity = new HttpEntity<>(targetStudent, headers);
		try{
			ResponseEntity<String> responseEntity = restTemplate.exchange(
					"http://localhost:4040/student/" + studentId,
					HttpMethod.PATCH,
					requestEntity,
					String.class
			);
			Assertions.assertEquals(HttpStatus.UNAUTHORIZED,responseEntity.getStatusCode());
		}catch (HttpClientErrorException e){
		}
	}

	//checking deleteStudent with invalid student id
	@Test
	public void deleteStudentWithInvalidStudentId(){
		UUID studentId = UUID.fromString("1e66edf8-851b-4af7-99b7-4834aec9929d");

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNb2dhbmVzYW4iLCJpYXQiOjE2ODkxNDcyNzksImV4cCI6MTY4OTIzMzY3OX0.EMyXO_73Uhl6wd8z4GbbAzSsIMk6rHOTYePkMUY1rRZ27rryUVNOIR6wj3pFZC3HzqFLscVISygTkaxObnCvQg");

		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

		HttpEntity<Student> requestEntity = new HttpEntity<>(headers);

		try{
			ResponseEntity<String> responseEntity = restTemplate.exchange(
					"http://localhost:4040/student/" + studentId,
					HttpMethod.DELETE,
					requestEntity,
					String.class
			);

			Assertions.assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
		}catch (HttpClientErrorException e){

		}

	}

	//checking delete student with valid student id
	@Test
	public void deleteStudentWithValidStudentId(){
		UUID studentId = UUID.fromString("fd6a5e22-bc3c-4fbd-ba44-bc93061a6f71");

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(validJWT);

		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

		HttpEntity<Student> requestEntity = new HttpEntity<>(headers);

		try{
			ResponseEntity<String> responseEntity = restTemplate.exchange(
					"http://localhost:4040/student/" + studentId,
					HttpMethod.DELETE,
					requestEntity,
					String.class
			);

			Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
		}catch (HttpClientErrorException e){

		}
	}

	//checking delete student with invalid jwt
	@Test
	public void deleteStudentWithInvalidJWT(){
		UUID studentId = UUID.fromString("fcff34b7-3530-42b9-88fc-8a3274f788c3");

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("Random String");

		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

		HttpEntity<Student> requestEntity = new HttpEntity<>(headers);

		try{
			ResponseEntity<String> responseEntity = restTemplate.exchange(
					"http://localhost:4040/student/" + studentId,
					HttpMethod.DELETE,
					requestEntity,
					String.class
			);

			Assertions.assertEquals(HttpStatus.UNAUTHORIZED,responseEntity.getStatusCode());
			Assertions.assertEquals("Incorrect Jwt Token format",responseEntity.getBody());
		}catch (HttpClientErrorException e){

		}
	}

}
