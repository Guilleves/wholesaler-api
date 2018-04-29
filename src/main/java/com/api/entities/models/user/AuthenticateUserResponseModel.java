package com.api.entities.models.user;

public class AuthenticateUserResponseModel {
    private String token;

    /**
    * Returns value of token
    * @return
    */
    public String getToken() {
        return token;
    }

    /**
    * Sets new value of token
    * @param
    */
    public void setToken(String token) {
        this.token = token;
    }
}
