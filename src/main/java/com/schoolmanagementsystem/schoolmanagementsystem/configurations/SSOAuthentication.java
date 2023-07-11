package com.schoolmanagementsystem.schoolmanagementsystem.configurations;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SSOAuthentication implements Authentication {

    private AuthPrincipal authPrincipal;
    private boolean authentication = false;

    @Override
    public String getName() {
        return "USER_NAME";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return authPrincipal;
    }

    public void setPrincipal(AuthPrincipal principal) {
        authPrincipal = principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authentication;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authentication = isAuthenticated;
    }
}

