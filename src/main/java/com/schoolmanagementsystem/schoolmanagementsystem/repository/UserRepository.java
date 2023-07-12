package com.schoolmanagementsystem.schoolmanagementsystem.repository;

import com.schoolmanagementsystem.schoolmanagementsystem.models.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String > {
    User findByUserName(String userName);
}
