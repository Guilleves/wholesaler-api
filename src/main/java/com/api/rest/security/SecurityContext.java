package com.api.rest.security;

public class SecurityContext implements javax.ws.rs.core.SecurityContext {
    private UserPrincipal userPrincipal;

    public SecurityContext(UserPrincipal user) {
        userPrincipal = user;
    }

    @Override
    public UserPrincipal getUserPrincipal() {
        return userPrincipal;
    }

    @Override
    public boolean isUserInRole(String s) {
        return true;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
