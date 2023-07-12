package com.schoolmanagementsystem.schoolmanagementsystem.services;

import com.schoolmanagementsystem.schoolmanagementsystem.configurations.JwtService;
import com.schoolmanagementsystem.schoolmanagementsystem.exceptions.ValidationException;
import com.schoolmanagementsystem.schoolmanagementsystem.models.db.User;
import com.schoolmanagementsystem.schoolmanagementsystem.models.request.user.CreateUser;
import com.schoolmanagementsystem.schoolmanagementsystem.models.request.user.UserLogin;
import com.schoolmanagementsystem.schoolmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserService {


    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected JwtService jwtService;


    public void signupUser(String traceId, CreateUser createUser) throws ValidationException {
        User userFromDb = fetchUserByUserName(createUser.getUserName());
        if (userFromDb != null) throw new ValidationException("Username already exist");
        User user = new User();
        user.setUserName(createUser.getUserName());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(createUser.getPassword()));
        user.setEmailId(createUser.getEmailId());
        user.setPhoneNumber(createUser.getPhoneNumber());
        userRepository.save(user);
    }

    public String login(String traceId, UserLogin userLogin) throws ValidationException {
        User userFromDb = fetchUserByUserName(userLogin.getUserName());
        if (userFromDb == null) throw new ValidationException("User does not exist");
        if (!passwordEncoder.matches(userLogin.getPassword(),userFromDb.getPassword())) {
            throw new ValidationException("Email Id or password is incorrect");
        }
        String token = jwtService.generateToken(userFromDb.getUserName());
        return token;
    }


    public User fetchUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
