package com.schoolmanagementsystem.schoolmanagementsystem.models.db;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String userName;
    private String password;
    private String emailId;
    private String phoneNumber;
    private boolean active = false;
    private int resetPin = 0;
    private Timestamp resetPinValidTime = Timestamp.from(Instant.now());

    public User(String userName, String password, String emailId, String phoneNumber, boolean active,
                int resetPin, Timestamp resetPinValidTime) {
        this.userName = userName;
        this.password = password;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.resetPin = resetPin;
        this.resetPinValidTime = resetPinValidTime;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public int getResetPin() {
        return resetPin;
    }

    public Timestamp getResetPinValidTime() {
        return resetPinValidTime;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setResetPin(int resetPin) {
        this.resetPin = resetPin;
    }

    public void setResetPinValidTime(Timestamp resetPinValidTime) {
        this.resetPinValidTime = resetPinValidTime;
    }
}
