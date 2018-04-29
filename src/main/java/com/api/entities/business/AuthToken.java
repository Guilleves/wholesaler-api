package com.api.entities.business;

// #region Imports

import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;

// #endregion

public class AuthToken {
    private int id;
    private String token;
    private User user;
    private Date created;

    /**
    * Default empty AuthToken constructor
    */
    public AuthToken() {
        super();
    }

    /**
    * Default AuthToken constructor
    */
    public AuthToken(int id, String token, User user, Date created) {
        super();
        this.id = id;
        this.token = token;
        this.user = user;
        this.created = created;
    }

    /**
    * Constructor for MySql
    */
    public AuthToken(ResultSet rs) {
        super();

        try {
            this.id = rs.getInt("id");
            this.token = rs.getString("token");
            this.created = rs.getTimestamp("created");
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
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

    /**
    * Returns value of user
    * @return
    */
    public User getUser() {
        return user;
    }

    /**
    * Sets new value of user
    * @param
    */
    public void setUser(User user) {
        this.user = user;
    }

    /**
    * Returns value of created
    * @return
    */
    public Date getCreated() {
        return created;
    }

    /**
    * Sets new value of created
    * @param
    */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
    * Create string representation of AuthToken for printing
    * @return
    */
    @Override
    public String toString() {
        return "AuthToken [id=" + id + ", token=" + token + ", user=" + user + ", created=" + created + "]";
    }
}
