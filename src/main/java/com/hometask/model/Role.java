package com.hometask.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    user, admin;

    @Override
    public String getAuthority() {
        return name();
    }


    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
