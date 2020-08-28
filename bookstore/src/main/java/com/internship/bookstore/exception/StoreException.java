package com.internship.bookstore.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class StoreException extends RuntimeException {

	private static final long serialVersionUID = 8015647556248899491L;

	private HttpStatus statusCode;
	private String message;

	public StoreException(HttpStatus statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

}
