package com.schoolmanagementsystem.schoolmanagementsystem.services;

import com.schoolmanagementsystem.schoolmanagementsystem.exceptions.ValidationException;
import com.schoolmanagementsystem.schoolmanagementsystem.models.db.User;
import com.schoolmanagementsystem.schoolmanagementsystem.models.request.user.CreateUser;
import com.schoolmanagementsystem.schoolmanagementsystem.models.request.user.UserLogin;

public interface UserService {
    /**
     * Function to create new user in database
     * @param traceId
     * @param createUser
     * @throws ValidationException
     */
    void signUpUser(String traceId, CreateUser createUser) throws ValidationException;

    /**
     * Function to authenticate the user
     * @param traceId
     * @param userLogin
     * @throws ValidationException
     */
    String login(String traceId, UserLogin userLogin) throws ValidationException;

    /**
     * Function to get user details with userName
     * @param userName
     * @return
     * @throws ValidationException
     */
    User fetchUserByUserName(String userName) throws  ValidationException;
}
