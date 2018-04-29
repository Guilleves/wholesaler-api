package com.api.entities.business;

// #region Imports

import java.util.Date;
import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.SQLException;

// #endregion

public class User {
    private int id;
    private String username, password, firstName, lastName,
        primaryAddress1, primaryAddress2, city, province, country,
        primaryPhone, secondaryPhone, email;
    private Date birthdate;
    private ArrayList<Role> roles;

    /**
    * Default empty User constructor
    */
    public User() {
        super();

        roles = new ArrayList<Role>();
    }

    /**
    * Client constructor for SQL
    */
    public User(ResultSet rs) {
        super();
        try {
            this.id = rs.getInt("id");
            this.username = rs.getString("username");
            this.password = rs.getString("password");
            this.firstName = rs.getString("firstName");
            this.lastName = rs.getString("lastName");
            this.primaryAddress1 = rs.getString("primaryAddress1");
            this.primaryAddress2 = rs.getString("primaryAddress2");
            this.city = rs.getString("city");
            this.province = rs.getString("province");
            this.country = rs.getString("country");
            this.primaryPhone = rs.getString("primaryPhone");
            this.secondaryPhone = rs.getString("secondaryPhone");
            this.email = rs.getString("email");
            this.birthdate = rs.getDate("birthdate");
        }
        catch(SQLException e) {

        }
    }

    /**
    * Default User constructor
    */
    public User(int id, String username, String password, String firstName, String lastName, String primaryAddress1, String primaryAddress2, String city, String province, String country, String primaryPhone, String secondaryPhone, String email, Date birthdate) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.primaryAddress1 = primaryAddress1;
        this.primaryAddress2 = primaryAddress2;
        this.city = city;
        this.province = province;
        this.country = country;
        this.primaryPhone = primaryPhone;
        this.secondaryPhone = secondaryPhone;
        this.email = email;
        this.birthdate = birthdate;
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
    * Returns value of password
    * @return
    */
    public String getPassword() {
        return password;
    }

    /**
    * Sets new value of password
    * @param
    */
    public void setPassword(String password) {
        this.password = password;
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
    * Returns complete name
    * @return
    */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    /**
    * Returns value of primaryAddress1
    * @return
    */
    public String getPrimaryAddress1() {
        return primaryAddress1;
    }

    /**
    * Sets new value of primaryAddress1
    * @param
    */
    public void setPrimaryAddress1(String primaryAddress1) {
        this.primaryAddress1 = primaryAddress1;
    }

    /**
    * Returns value of primaryAddress2
    * @return
    */
    public String getPrimaryAddress2() {
        return primaryAddress2;
    }

    /**
    * Sets new value of primaryAddress2
    * @param
    */
    public void setPrimaryAddress2(String primaryAddress2) {
        this.primaryAddress2 = primaryAddress2;
    }

    /**
    * Returns value of city
    * @return
    */
    public String getCity() {
        return city;
    }

    /**
    * Sets new value of city
    * @param
    */
    public void setCity(String city) {
        this.city = city;
    }

    /**
    * Returns value of province
    * @return
    */
    public String getProvince() {
        return province;
    }

    /**
    * Sets new value of province
    * @param
    */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
    * Returns value of country
    * @return
    */
    public String getCountry() {
        return country;
    }

    /**
    * Sets new value of country
    * @param
    */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
    * Returns value of primaryPhone
    * @return
    */
    public String getPrimaryPhone() {
        return primaryPhone;
    }

    /**
    * Sets new value of primaryPhone
    * @param
    */
    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    /**
    * Returns value of secondaryPhone
    * @return
    */
    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    /**
    * Sets new value of secondaryPhone
    * @param
    */
    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    /**
    * Returns value of email
    * @return
    */
    public String getEmail() {
        return email;
    }

    /**
    * Sets new value of email
    * @param
    */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
    * Returns value of birthdate
    * @return
    */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
    * Sets new value of birthdate
    * @param
    */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void addRole(Role role) {
        if (roles == null)
        roles = new ArrayList<Role>();

        roles.add(role);
    }

    /**
    * Create string representation of User for printing
    * @return
    */
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + ", primaryAddress1=" + primaryAddress1 + ", primaryAddress2=" + primaryAddress2 + ", city=" + city + ", province=" + province + ", country=" + country + ", primaryPhone=" + primaryPhone + ", secondaryPhone=" + secondaryPhone + ", email=" + email + ", birthdate=" + birthdate + "]";
    }

    /**
    * Returns value of role
    * @return
    */
    public ArrayList<Role> getRoles() {
        return roles;
    }

    /**
    * Sets new value of role
    * @param
    */
    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }
}
