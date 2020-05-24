package com.bouncetoacure.exceptions;

public class EmptyFieldException extends Exception {

	String error;
	
	public EmptyFieldException() {
		super();             // call superclass constructor
	    error = "unknown";
	}

	public EmptyFieldException(String err) {
		super(err);     // call super class constructor
	    error = err;  // save message
	}

	public String getMessage()
	{
		return error;
	}

}
