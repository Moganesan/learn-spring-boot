package com.schoolmanagementsystem.schoolmanagementsystem.controllers;

import com.schoolmanagementsystem.schoolmanagementsystem.exceptions.ValidationException;
import com.schoolmanagementsystem.schoolmanagementsystem.models.request.user.CreateUser;
import com.schoolmanagementsystem.schoolmanagementsystem.models.request.user.UserLogin;
import com.schoolmanagementsystem.schoolmanagementsystem.models.response.ResponseMessage;
import com.schoolmanagementsystem.schoolmanagementsystem.models.response.ResponseStatus;
import com.schoolmanagementsystem.schoolmanagementsystem.services.UserService;
import com.schoolmanagementsystem.schoolmanagementsystem.utils.Utils;
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

    @PostMapping(value = "/register")
    public ResponseEntity<?> signupUser(@RequestBody CreateUser createUser) {
        String traceId  = Utils.getTrackingId();
        try {
            userService.signupUser(traceId,createUser);
            return new ResponseEntity<>(new ResponseMessage(201, ResponseStatus.Successful,"Signup Successful"), HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new ResponseMessage(403, ResponseStatus.Failure,e.getExceptionMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new ResponseMessage(500, ResponseStatus.Failure,"Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> signupUser(@RequestBody UserLogin userLogin) {
        String traceId  = Utils.getTrackingId();
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
