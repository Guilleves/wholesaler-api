package com.api.logic.validations;

public class ServerError {
    private String errorMessage;

    public ServerError() {

    }

    public ServerError(Exception e) {
        errorMessage = e.getMessage();
    }

    public ServerError(String e) {
        errorMessage = e;
    }

    /**
    * Returns value of errorMessage
    * @return
    */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
    * Sets new value of errorMessage
    * @param
    */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
