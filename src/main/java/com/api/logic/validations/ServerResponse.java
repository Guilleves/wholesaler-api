package com.api.logic.validations;

// #region Imports

import java.util.ArrayList;

// #endregion

public class ServerResponse extends Exception {
    private static final long serialVersionUID = 1L;
    private ArrayList<ServerError> errors;

    public void addError(Exception ex) {
        if (errors == null)
        errors = new ArrayList<ServerError>();
        
        errors.add(new ServerError(ex));
    }

    public void addError(String error) {
        if (errors == null)
        errors = new ArrayList<ServerError>();

        errors.add(new ServerError(error));
    }

    public boolean getStatus() {
        return (errors == null || errors.isEmpty());
    }

    public ArrayList<ServerError> getErrores() {
        return this.errors;
    }
}
