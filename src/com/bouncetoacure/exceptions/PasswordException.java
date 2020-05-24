package com.bouncetoacure.exceptions;

public class PasswordException extends Exception {

	String error;
	
	public PasswordException() {
		super();             // call superclass constructor
	    error = "unknown";
	}

	public PasswordException(String err) {
		super(err);     // call super class constructor
	    error = err;  // save message
	}

	public String getMessage()
	{
		return error;
	}
}
