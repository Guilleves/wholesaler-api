package com.api.logic.business;

// #region Imports
import com.api.entities.models.BaseSearchResponse;
import com.api.entities.models.user.GetUsersRequest;
import com.api.entities.business.Organization;
import java.sql.SQLException;
import java.util.ArrayList;

import com.api.data.business.UserDataAccess;

import com.api.entities.business.User;
import com.api.entities.models.user.GetUserRequest;
import com.api.entities.models.user.GetUserResponse;
import com.api.entities.models.user.SaveUserRequest;
import com.api.entities.models.user.LoginRequest;
import com.api.entities.models.organization.GetOrganizationResponse;
import com.api.entities.models.user.LoginResponse;

import com.api.logic.validations.ApiException;

// #endregion

public class UserLogic {
  private UserDataAccess uda;
  private SecurityLogic sl;

  // #region Constructors

  public UserLogic() {
    uda = new UserDataAccess();
    sl = new SecurityLogic();
  }

  // #endregion

  // #region UserSetup

  public int countUsers(User loggedUser) throws ApiException {
    try {
      return uda.countUsers(loggedUser.getOrganization().getId());
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  public boolean deleteUser(int userId) throws ApiException {
    try {
      return uda.deleteUser(userId);
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  public GetUserResponse getUser(GetUserRequest request) throws ApiException {
    try {
      ApiException ex = validateGetUser(request.getUserId());

      if (!ex.isOk())
      throw ex;

      // Fetch the user.
      User user = uda.getUser(request.getUserId());

      if (user == null)
      throw ex.addError("User not found.");

      // Generate de response object (future JSON).
      GetUserResponse response = new GetUserResponse(
      user.getId(),
      user.getFirstName(),
      user.getLastName(),
      user.getUsername(),
      user.getEmail(),
      new GetOrganizationResponse(
      user.getOrganization().getId(),
      user.getOrganization().getName(),
      user.getOrganization().getLegalName(),
      user.getOrganization().getCuit(),
      user.getOrganization().getRole()
      )
      );

      return response;
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  public BaseSearchResponse getUsers(GetUsersRequest request, User loggedUser) throws ApiException {
    try {
      ArrayList<GetUserResponse> response = new ArrayList<GetUserResponse>();

      // Fetch the user list.
      ArrayList<User> users = uda.getUsers(
      request.getKeyword(),
      request.getOrderBy(),
      request.getPageIndex(),
      request.getPageSize(),
      loggedUser.getOrganization().getId()
      );

      if (users == null || users.isEmpty()) {
        throw new ApiException("No users found.");
      }

      // Generate the response (future JSON).
      for (User user : users) {
        response.add(new GetUserResponse(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getUsername(),
        user.getEmail(),
        new GetOrganizationResponse(
        user.getOrganization().getId(),
        user.getOrganization().getName(),
        user.getOrganization().getLegalName(),
        user.getOrganization().getCuit(),
        user.getOrganization().getRole()
        )
        ));
      }

      return new BaseSearchResponse(uda.countSearch(request.getKeyword()), response);
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  public void saveUser(SaveUserRequest request) throws ApiException {
    try {
      User user = new User(
      request.getId(),
      request.getFirstName(),
      request.getLastName(),
      request.getUsername(),
      request.getPassword(),
      request.getEmail()
      );

      ApiException ex = validateSaveUser(user, request.getRepeatPassword());

      if (!ex.isOk())
      throw ex;

      // If password is being created/updated, ecrypt it
      if (!(user.getPassword() == null || user.getPassword().isEmpty())) {
        try {
          user.setPassword(sl.encryptPassword(user.getPassword()));
        } // TODO: Dunno if should specify the exception, since I'll handle all of them equally
        catch(Exception e) {
          throw ex.addError(e);
        }
      }

      uda.updateUser(user);
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  // #endregion

  // #region Security

  public LoginResponse signup(SaveUserRequest request) throws ApiException {
    try {
      User user = new User(
      0,
      request.getFirstName(),
      request.getLastName(),
      request.getUsername(),
      request.getPassword(),
      request.getEmail()
      );

      Organization org = new Organization();
      org.setId(request.getOrganizationId());
      user.setOrganization(org);

      // Fetch user.
      User dbUser = uda.getUser(request.getUsername());

      if (dbUser != null)
      throw new ApiException("This username is taken");

      // Validate.
      ApiException ex = validateSignup(user, request.getRepeatPassword());

      if (!ex.isOk())
      throw ex;

      try {
        user.setPassword(sl.encryptPassword(user.getPassword()));
      } // Dunno if should specify the exception, since I'll handle all of them equally
      catch(Exception e) {
        throw new ApiException(e);
      }

      User createdUser = uda.createUser(user);

      String token = "";

      try {
        token = sl.issueAuthToken(createdUser.getId());
      }
      catch(Exception e) {
        throw new ApiException(e);
      }

      return new LoginResponse(
      token,
      new GetUserResponse(
      createdUser.getId(),
      createdUser.getFirstName(),
      createdUser.getLastName(),
      createdUser.getUsername(),
      createdUser.getEmail(), new GetOrganizationResponse(
      user.getOrganization().getId(),
      user.getOrganization().getName(),
      user.getOrganization().getLegalName(),
      user.getOrganization().getCuit(),
      user.getOrganization().getRole()
      )),
      new GetOrganizationResponse(
      createdUser.getOrganization().getId(),
      createdUser.getOrganization().getName(),
      createdUser.getOrganization().getLegalName(),
      createdUser.getOrganization().getCuit(),
      createdUser.getOrganization().getRole())
      );
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  public LoginResponse login(LoginRequest request) throws ApiException {
    try {
      LoginResponse response = new LoginResponse();

      boolean couldAuthenticate = false;
      String username = request.getUsername();
      String password = request.getPassword();

      // Validate input fields, and throw exception if invalid.
      ApiException ex = validateAuthentication(username, password);

      if (!ex.isOk())
      throw ex;

      // Fetch user.
      User dbUser = uda.getUser(username);

      if (dbUser == null)
      throw ex.addError("Invalid username or password.");

      // First, try to authenticate against the db.
      try {
        couldAuthenticate = sl.validatePassword(password, dbUser.getPassword());
      }
      catch(Exception e) {
        throw new ApiException("Invalid username or password.");
      }

      // If the password comparison succeed.
      if (couldAuthenticate) {
        try {
          // Set the token in the response body.
          response.setToken(sl.issueAuthToken(dbUser.getId()));
        }
        catch(Exception e) {
          throw ex.addError(e);
        }
      }
      else
      throw new ApiException("Invalid username or password.");

      response.setUser(new GetUserResponse(
      dbUser.getId(),
      dbUser.getFirstName(),
      dbUser.getLastName(),
      dbUser.getUsername(),
      dbUser.getEmail(),
      new GetOrganizationResponse(
      dbUser.getOrganization().getId(),
      dbUser.getOrganization().getName(),
      dbUser.getOrganization().getLegalName(),
      dbUser.getOrganization().getCuit(),
      dbUser.getOrganization().getRole()
      ))
      );

      response.setOrganization(new GetOrganizationResponse(
      dbUser.getOrganization().getId(),
      dbUser.getOrganization().getName(),
      dbUser.getOrganization().getLegalName(),
      dbUser.getOrganization().getCuit(),
      dbUser.getOrganization().getRole())
      );

      return response;
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  // #endregion

  // #region Validations

  private ApiException validateAuthentication(String username, String password) {
    ApiException sr = new ApiException();

    if (username == null || username.isEmpty())
    sr.addError("Please provide a username.");

    if (password == null || password.isEmpty())
    sr.addError("Please provide a password.");

    // if (passwordRequirements(password))

    return sr;
  }

  private ApiException validateGetUser(int userId) {
    ApiException sr = new ApiException();

    if (userId <= 0)
    sr.addError("Please provide a valid user id");

    return sr;
  }

  private ApiException validateSaveUser(User user, String repeatPassword) {
    ApiException sr = new ApiException();

    if (user.getUsername() == null || user.getUsername().isEmpty())
    sr.addError("Username missing");

    if (user.getFirstName() == null || user.getFirstName().isEmpty())
    sr.addError("Firstname missing");

    if (user.getLastName() == null || user.getLastName().isEmpty())
    sr.addError("Surname missing");

    if (user.getEmail() == null || user.getEmail().isEmpty())
    sr.addError("eMail missing");

    return sr;
  }

  private ApiException validateSignup(User user, String repeatPassword) {
    ApiException sr = new ApiException();

    if (user.getUsername() == null || user.getUsername().isEmpty())
    sr.addError("Username missing");

    if (user.getFirstName() == null || user.getFirstName().isEmpty())
    sr.addError("Firstname missing");

    if (user.getLastName() == null || user.getLastName().isEmpty())
    sr.addError("Surname missing");

    if (user.getPassword() == null || user.getPassword().isEmpty())
    sr.addError("Password missing");

    if (repeatPassword == null || repeatPassword.isEmpty())
    sr.addError("Repeat password missing");

    if (user.getEmail() == null || user.getEmail().isEmpty())
    sr.addError("eMail missing");

    if (user.getPassword() != null && repeatPassword != null && !user.getPassword().equals(repeatPassword))
    sr.addError("Passwords don't match.");

    return sr;
  }

  // #endregion
}
