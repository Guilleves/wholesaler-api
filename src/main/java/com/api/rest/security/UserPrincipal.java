package com.api.rest.security;

// #region Imports

import java.util.ArrayList;

import com.api.entities.business.User;

// #endregion

public class UserPrincipal implements java.security.Principal {
    private int id;
    private String username, firstName, lastName, role;

    /**
    * Default empty UserPrincipal constructor
    */
    public UserPrincipal() {
        super();
    }

    /**
    * User constructor
    */
    public UserPrincipal(User user) {
        super();
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getOrganization().getRole();
    }

    /**
    * Default UserPrincipal constructor
    */
    public UserPrincipal(int id, String username, String firstName, String lastName, ArrayList<String> roles) {
        super();
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
    * Returns value of id
    * @return
    */
    public int getId() {
        return id;
    }

    /**
    * Sets new value of id
    * @param
    */
    public void setId(int id) {
        this.id = id;
    }

    /**
    * Returns value of username
    * @return
    */
    public String getUsername() {
        return username;
    }

    /**
    * Sets new value of username
    * @param
    */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
    * Returns value of firstName
    * @return
    */
    public String getFirstName() {
        return firstName;
    }

    /**
    * Sets new value of firstName
    * @param
    */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
    * Returns value of lastName
    * @return
    */
    public String getLastName() {
        return lastName;
    }

    /**
    * Sets new value of lastName
    * @param
    */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
    * Returns value of lastName
    * @return
    */
    public String getRole() {
        return role;
    }

    /**
    * Sets new value of lastName
    * @param
    */
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getName() {
        return this.firstName + " " + this.lastName;
    }
}
