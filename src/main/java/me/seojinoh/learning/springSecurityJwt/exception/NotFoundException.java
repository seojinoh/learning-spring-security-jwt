package me.seojinoh.learning.springSecurityJwt.exception;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1429956729378782122L;

	public NotFoundException() {
	}

	public NotFoundException(String message) {
		super(message);
	}

}
