package com.schoolmanagementsystem.schoolmanagementsystem.configurations;

public class AuthPrincipal {
    private String userName;

    private String role;

    public AuthPrincipal() {
    }

    public AuthPrincipal(String userName, String role) {
        this.userName = userName;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
