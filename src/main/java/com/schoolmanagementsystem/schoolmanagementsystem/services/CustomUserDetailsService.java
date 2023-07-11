package com.schoolmanagementsystem.schoolmanagementsystem.services;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        if ("user".equals(username)){
            return User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();
        }
        throw new UsernameNotFoundException("User not found with username"+username);
    }
}
