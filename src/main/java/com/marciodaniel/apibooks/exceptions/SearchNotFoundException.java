package com.marciodaniel.apibooks.exceptions;

public class SearchNotFoundException extends RuntimeException {

	public SearchNotFoundException(String message) {
		super(message);
	}

	public SearchNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
