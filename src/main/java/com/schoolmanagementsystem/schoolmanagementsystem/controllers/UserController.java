package com.schoolmanagementsystem.schoolmanagementsystem.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolmanagementsystem.schoolmanagementsystem.exceptions.ValidationException;
import com.schoolmanagementsystem.schoolmanagementsystem.models.request.user.CreateUser;
import com.schoolmanagementsystem.schoolmanagementsystem.models.request.user.UserLogin;
import com.schoolmanagementsystem.schoolmanagementsystem.models.response.ResponseMessage;
import com.schoolmanagementsystem.schoolmanagementsystem.models.response.ResponseStatus;
import com.schoolmanagementsystem.schoolmanagementsystem.services.UserService;
import com.schoolmanagementsystem.schoolmanagementsystem.services.impl.UserServiceImpl;
import com.schoolmanagementsystem.schoolmanagementsystem.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * API to register new user
     * @param createUser
     * @return 201 success message
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/register")
    public ResponseEntity<?> signupUser(@RequestBody CreateUser createUser) throws JsonProcessingException {
        String traceId  = Utils.getTrackingId();
        String userJSON = objectMapper.writeValueAsString(createUser);
        logger.info("{}: Post Request to register new user, User: {}",traceId,userJSON);
        try {
            userService.signUpUser(traceId,createUser);
            return new ResponseEntity<>(new ResponseMessage(201, ResponseStatus.Successful,"Signup Successful"), HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new ResponseMessage(403, ResponseStatus.Failure,e.getExceptionMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new ResponseMessage(500, ResponseStatus.Failure,"Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API to login
     * @param userLogin
     * @return 200 success message with jwt token
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/login")
    public ResponseEntity<?> signupUser(@RequestBody UserLogin userLogin) throws JsonProcessingException {
        String traceId  = Utils.getTrackingId();
        String userJSON = objectMapper.writeValueAsString(userLogin);
        logger.info("{}: Post Request to login, User: {}",traceId,userJSON);
        try {
            String token = userService.login(traceId,userLogin);
            return new ResponseEntity<>(new ResponseMessage(200, ResponseStatus.Successful,token), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new ResponseMessage(403, ResponseStatus.Failure,e.getLocalizedMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(500, ResponseStatus.Failure,"Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
