package com.api.logic.validations;

public class ApiError {
    private Exception error;

    public ApiError(Exception e) {
        error = e;
    }

    public ApiError(String e) {
        error = new Exception(e);
    }

	/**
	* Returns value of error
	* @return
	*/
	public Exception getError() {
		return error;
	}

	/**
	* Sets new value of error
	* @param
	*/
	public void setError(Exception error) {
		this.error = error;
	}

	/**
	* Create string representation of ApiError for printing
	* @return
	*/
	@Override
	public String toString() {
		return error.getMessage();
	}
}
