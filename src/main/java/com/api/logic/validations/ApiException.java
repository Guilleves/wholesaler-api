package com.api.logic.validations;

import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;

public class ApiException extends Exception {
    private static final long serialVersionUID = 1L;

    private ArrayList<ApiError> errors;
    private Status status;

    // #region Constructors

    public ApiException() {
        super();
        errors = new ArrayList<ApiError>();
    }

    public ApiException(Exception ex) {
        super();
        errors = new ArrayList<ApiError>();
        addError(ex);
    }

    public ApiException(String message) {
        super();
        errors = new ArrayList<ApiError>();
        addError(message);
    }

    public ApiException(String message, Status status) {
        super();
        errors = new ArrayList<ApiError>();
        addError(message);
        this.status = status;
    }

    public ApiException(Exception ex, Status status) {
        super();
        errors = new ArrayList<ApiError>();
        addError(ex);
        this.status = status;
    }

    // #endregion

    // #region Public methods

    public ApiException addError(Exception ex) {
        errors.add(new ApiError(ex));

        return this;
    }

    public ApiException addError(String message) {
        errors.add(new ApiError(message));

        return this;
    }

    public boolean isOk() {
        return (errors == null || errors.isEmpty());
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        // If set, return the status.
        if (this.status != null)
            return this.status;

        // If not set, check if there are errors. If not, return OK.
        if (isOk())
            return Status.OK;

        // Otherwise return 500 as default.
        return Status.INTERNAL_SERVER_ERROR;
    }

    public ArrayList<String> getErrors() {
        ArrayList<String> messages = new ArrayList<String>();

        for(ApiError error : errors) {
            messages.add(error.toString());
        }
        return messages;
    }

    // #endregion
}
