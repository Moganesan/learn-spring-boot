package com.schoolmanagementsystem.schoolmanagementsystem.services.impl;

import com.schoolmanagementsystem.schoolmanagementsystem.configurations.JwtService;
import com.schoolmanagementsystem.schoolmanagementsystem.exceptions.ValidationException;
import com.schoolmanagementsystem.schoolmanagementsystem.models.db.User;
import com.schoolmanagementsystem.schoolmanagementsystem.models.request.user.CreateUser;
import com.schoolmanagementsystem.schoolmanagementsystem.models.request.user.UserLogin;
import com.schoolmanagementsystem.schoolmanagementsystem.repository.UserRepository;
import com.schoolmanagementsystem.schoolmanagementsystem.services.StudentService;
import com.schoolmanagementsystem.schoolmanagementsystem.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {


    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected JwtService jwtService;


    @Override
    public void signUpUser(String traceId, CreateUser createUser) throws ValidationException {
        User userFromDb = fetchUserByUserName(createUser.getUserName());
        logger.info("{}: Function start: UserServiceImpl.signUpUser()",traceId);
        if (userFromDb != null) throw new ValidationException("Username already exist");
        User user = new User();
        user.setUserName(createUser.getUserName());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(createUser.getPassword()));
        user.setEmailId(createUser.getEmailId());
        user.setPhoneNumber(createUser.getPhoneNumber());
        userRepository.save(user);
        logger.info("{}: Function end: UserServiceImpl.signUpUser()",traceId);
    }


    @Override
    public String login(String traceId, UserLogin userLogin) throws ValidationException {
        User userFromDb = fetchUserByUserName(userLogin.getUserName());
        logger.info("{}: Function start: UserServiceImpl.login()",traceId);
        if (userFromDb == null) throw new ValidationException("User does not exist");
        if (!passwordEncoder.matches(userLogin.getPassword(),userFromDb.getPassword())) {
            throw new ValidationException("Email Id or password is incorrect");
        }
        String token = jwtService.generateToken(userFromDb.getUserName());
        logger.info("{}: Function end: UserServiceImpl.signUpUser()",traceId);
        return token;
    }


    public User fetchUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
