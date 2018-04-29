package com.api.logic.business;

// #region Imports

import java.util.ArrayList;

<<<<<<< HEAD
import java.io.UnsupportedEncodingException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.api.data.business.UserDataAccess;

import com.api.entities.business.User;
import com.api.entities.models.user.AuthenticateUserRequestModel;
import com.api.entities.models.user.AuthenticateUserResponseModel;

import com.api.logic.validations.ServerResponse;

import com.auth0.jwt.exceptions.JWTCreationException;

=======
import com.api.data.business.UserDataAccess;

import com.api.entities.business.User;
import com.api.entities.models.user.GetUserRequest;
import com.api.entities.models.user.GetUserResponse;
import com.api.entities.models.user.SaveUserRequest;
import com.api.entities.models.user.LoginRequest;
import com.api.entities.models.user.LoginResponse;

import com.api.logic.validations.ServerResponse;

>>>>>>> b866114a19142079da6e615bfd09b5a45fe75f99
// #endregion

public class UserLogic {
    private UserDataAccess uda;
<<<<<<< HEAD

    public UserLogic() {
        uda = new UserDataAccess();
    }

    // #region UserSetup

    public User getUser(int userId) throws ServerResponse {
        UserDataAccess uda = new UserDataAccess();
        ServerResponse sr = validateGetUser(userId);
=======
    private SecurityLogic sl;

    // #region Constructors

    public UserLogic() {
        uda = new UserDataAccess();
        sl = new SecurityLogic();
    }

    // #endregion

    // #region UserSetup

    public GetUserResponse getUser(GetUserRequest request) throws ServerResponse {
        ServerResponse sr = validateGetUser(request.getUserId());
>>>>>>> b866114a19142079da6e615bfd09b5a45fe75f99

        if (!sr.getStatus())
            throw sr;

<<<<<<< HEAD
        return uda.getUser(userId);
    }

    public ArrayList<User> getUsers() {
        UserDataAccess uda = new UserDataAccess();

        return uda.getUsers();
    }

    public boolean createUser(User user) throws ServerResponse {
        UserDataAccess uda = new UserDataAccess();
=======
        // Fetch the user.
        User user = uda.getUser(request.getUserId());

        if (user == null) {
            sr.addError("User not found.");
            throw sr;
        }

        // Generate de response object (future JSON).
        GetUserResponse response = new GetUserResponse(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getUsername(),
            user.getEmail()
        );

        return response;
    }

    // In case we actually use this method, it would probably need some filters.
    public ArrayList<GetUserResponse> getUsers() throws ServerResponse {
        ArrayList<GetUserResponse> response = new ArrayList<GetUserResponse>();

        // Fetch the user list.
        ArrayList<User> users = uda.getUsers();

        if (users == null || users.isEmpty())
            return null;

        // Generate the response (future JSON).
        for (User user : users) {
            response.add(new GetUserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail()
            ));
        }

        return response;
    }

    // Is this void? If something goes wrong, it would let me know throw the ServerResponse error
    public int saveUser(SaveUserRequest request) throws ServerResponse {
        User user = new User(
            request.getUserId(),
            request.getFirstName(),
            request.getLastName(),
            request.getUsername(),
            request.getPassword(),
            request.getEmail()
        );

>>>>>>> b866114a19142079da6e615bfd09b5a45fe75f99
        ServerResponse sr = validateSaveUser(user);

        if (!sr.getStatus())
            throw sr;

<<<<<<< HEAD
        return uda.saveUser(user);
=======
        // If password is being created/updated, ecrypt it
        if (!(user.getPassword() == null || user.getPassword().isEmpty())) {
            try {
                user.setPassword(sl.encryptPassword(user.getPassword()));
            } // Dunno if should specify the exception, since I'll handle all of them equally
            catch(Exception e) {
                sr.addError(e);
                throw sr;
            }
        }

        if (user.getId() == 0)
            return uda.createUser(user);
        else
            return uda.updateUser(user);
>>>>>>> b866114a19142079da6e615bfd09b5a45fe75f99
    }

    // #endregion

    // #region Security

<<<<<<< HEAD
    public AuthenticateUserResponseModel login(AuthenticateUserRequestModel request) throws ServerResponse {
        AuthenticateUserResponseModel response = new  AuthenticateUserResponseModel();
        SecurityLogic sl = new SecurityLogic();
=======
    public LoginResponse signup(SaveUserRequest request) throws ServerResponse {
        ServerResponse sr = new ServerResponse();

        User user = new User(
            0,
            request.getFirstName(),
            request.getLastName(),
            request.getUsername(),
            request.getPassword(),
            request.getEmail()
        );

        // Fetch user.
        User dbUser = uda.getUser(request.getUsername());

        if (dbUser != null) {
            sr.addError("This username is taken");
            throw sr;
        }

        int id = uda.createUser(user);

        String token = "";

        try {
            token = sl.issueAuthToken(id);
        }
        catch(Exception e) {
            sr.addError(e);
            throw sr;
        }

        return new LoginResponse(token);
    }

    public LoginResponse login(LoginRequest request) throws ServerResponse {
        LoginResponse response = new LoginResponse();
>>>>>>> b866114a19142079da6e615bfd09b5a45fe75f99

        boolean couldAuthenticate = false;
        String username = request.getUsername();
        String password = request.getPassword();

        // Validate input fields, and throw exception if invalid.
        ServerResponse sr = validateAuthentication(username, password);

        if (!sr.getStatus())
            throw sr;

<<<<<<< HEAD
=======
        // Fetch user.
>>>>>>> b866114a19142079da6e615bfd09b5a45fe75f99
        User dbUser = uda.getUser(username);

        if (dbUser == null) {
            sr.addError("User was not found.");
            throw(sr);
        }

        // First, try to authenticate against the db.
        try {
            couldAuthenticate = sl.validatePassword(password, dbUser.getPassword());
        }
<<<<<<< HEAD
        catch(NoSuchAlgorithmException e) {
            sr.addError(e);
            throw(sr);
        }
        catch (InvalidKeySpecException e) {
=======
        catch(Exception e) {
>>>>>>> b866114a19142079da6e615bfd09b5a45fe75f99
            sr.addError(e);
            throw(sr);
        }

        // If the password comparison succeed.
        if (couldAuthenticate) {
            try {
                // Set the token in the response body.
                response.setToken(sl.issueAuthToken(dbUser.getId()));
            }
<<<<<<< HEAD
            catch(UnsupportedEncodingException e) {
                sr.addError(e);
                throw(sr);
            }
            catch(JWTCreationException e) {
=======
            catch(Exception e) {
>>>>>>> b866114a19142079da6e615bfd09b5a45fe75f99
                sr.addError(e);
                throw(sr);
            }
        }

        return response;
    }

<<<<<<< HEAD
    public AuthenticateUserResponseModel signup(AuthenticateUserRequestModel request) {
        AuthenticateUserResponseModel response = new  AuthenticateUserResponseModel();

        return response;
    }

    // #endregion

    // #region Privates
=======
    // #endregion

    // #region Validations
>>>>>>> b866114a19142079da6e615bfd09b5a45fe75f99

    private ServerResponse validateAuthentication(String username, String password) {
        ServerResponse sr = new ServerResponse();

        if (username == null || username.isEmpty())
<<<<<<< HEAD
        sr.addError("El nombre de usuario no puede estar vacío.");

        if (password == null || password.isEmpty())
        sr.addError("La contraseña no puede estar vacía.");
=======
            sr.addError("El nombre de usuario no puede estar vacío.");

        if (password == null || password.isEmpty())
            sr.addError("La contraseña no puede estar vacía.");
>>>>>>> b866114a19142079da6e615bfd09b5a45fe75f99

        // if (passwordRequirements(password))

        return sr;
    }

    private ServerResponse validateGetUser(int userId) {
        ServerResponse sr = new ServerResponse();

        if (userId <= 0)
<<<<<<< HEAD
        sr.addError("Please provide a valid user id");
=======
            sr.addError("Please provide a valid user id");
>>>>>>> b866114a19142079da6e615bfd09b5a45fe75f99

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
