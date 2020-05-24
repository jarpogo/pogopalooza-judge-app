package com.bouncetoacure.exceptions;

public class MatchException extends Exception {

	String error;
	
	public MatchException() {
		super();             // call superclass constructor
	    error = "unknown";
	}

	public MatchException(String err) {
		super(err);     // call super class constructor
	    error = err;  // save message
	}

	public String getMessage()
	{
		return error;
	}
}
