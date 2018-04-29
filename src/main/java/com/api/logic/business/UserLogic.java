package com.api.logic.business;

// #region Imports

import java.util.ArrayList;

import java.io.UnsupportedEncodingException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.api.data.business.UserDataAccess;

import com.api.entities.business.User;
import com.api.entities.models.user.AuthenticateUserRequestModel;
import com.api.entities.models.user.AuthenticateUserResponseModel;

import com.api.logic.validations.ServerResponse;

import com.auth0.jwt.exceptions.JWTCreationException;

// #endregion

public class UserLogic {
    private UserDataAccess uda;

    public UserLogic() {
        uda = new UserDataAccess();
    }

    // #region UserSetup

    public User getUser(int userId) throws ServerResponse {
        UserDataAccess uda = new UserDataAccess();
        ServerResponse sr = validateGetUser(userId);

        if (!sr.getStatus())
            throw sr;

        return uda.getUser(userId);
    }

    public ArrayList<User> getUsers() {
        UserDataAccess uda = new UserDataAccess();

        return uda.getUsers();
    }

    public boolean createUser(User user) throws ServerResponse {
        UserDataAccess uda = new UserDataAccess();
        ServerResponse sr = validateSaveUser(user);

        if (!sr.getStatus())
            throw sr;

        return uda.saveUser(user);
    }

    // #endregion

    // #region Security

    public AuthenticateUserResponseModel login(AuthenticateUserRequestModel request) throws ServerResponse {
        AuthenticateUserResponseModel response = new  AuthenticateUserResponseModel();
        SecurityLogic sl = new SecurityLogic();

        boolean couldAuthenticate = false;
        String username = request.getUsername();
        String password = request.getPassword();

        // Validate input fields, and throw exception if invalid.
        ServerResponse sr = validateAuthentication(username, password);

        if (!sr.getStatus())
            throw sr;

        User dbUser = uda.getUser(username);

        if (dbUser == null) {
            sr.addError("User was not found.");
            throw(sr);
        }

        // First, try to authenticate against the db.
        try {
            couldAuthenticate = sl.validatePassword(password, dbUser.getPassword());
        }
        catch(NoSuchAlgorithmException e) {
            sr.addError(e);
            throw(sr);
        }
        catch (InvalidKeySpecException e) {
            sr.addError(e);
            throw(sr);
        }

        // If the password comparison succeed.
        if (couldAuthenticate) {
            try {
                // Set the token in the response body.
                response.setToken(sl.issueAuthToken(dbUser.getId()));
            }
            catch(UnsupportedEncodingException e) {
                sr.addError(e);
                throw(sr);
            }
            catch(JWTCreationException e) {
                sr.addError(e);
                throw(sr);
            }
        }

        return response;
    }

    public AuthenticateUserResponseModel signup(AuthenticateUserRequestModel request) {
        AuthenticateUserResponseModel response = new  AuthenticateUserResponseModel();

        return response;
    }

    // #endregion

    // #region Privates

    private ServerResponse validateAuthentication(String username, String password) {
        ServerResponse sr = new ServerResponse();

        if (username == null || username.isEmpty())
        sr.addError("El nombre de usuario no puede estar vacío.");

        if (password == null || password.isEmpty())
        sr.addError("La contraseña no puede estar vacía.");

        // if (passwordRequirements(password))

        return sr;
    }

    private ServerResponse validateGetUser(int userId) {
        ServerResponse sr = new ServerResponse();

        if (userId <= 0)
        sr.addError("Please provide a valid user id");

        return sr;
    }

    private ServerResponse validateSaveUser(User user) {
        ServerResponse sr = new ServerResponse();

        if (user.getUsername() == null || user.getUsername().isEmpty())
        sr.addError("El nombre de usuario es un campo obligatorio");

        if (user.getFirstName() == null || user.getFirstName().isEmpty())
        sr.addError("El primer nombre es un campo obligatorio");

        if (user.getLastName() == null || user.getLastName().isEmpty())
        sr.addError("El apellido es un campo obligatorio");

        return sr;
    }

    // #endregion
}
