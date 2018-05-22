package com.api.rest.security;

// #region Imports

import com.api.entities.business.User;

// #endregion

public class UserPrincipal implements java.security.Principal {
    private User user;

    /**
	* Default empty UserPrincipal constructor
	*/
	public UserPrincipal() {
		super();
	}

	/**
	* Default UserPrincipal constructor
	*/
	public UserPrincipal(User user) {
		super();
		this.user = user;
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

    @Override
    public String getName() {
        return this.user.getFirstName() + " " + this.user.getLastName();
    }
}
