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
        if (userPrincipal.getRoles() != null)
            return userPrincipal.getRoles().contains(s);
        
        return false;
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
