package com.verinite.cla.controlleradvice;

import java.util.Collections;
import java.util.List;

public class BadRequestException extends RuntimeException {

	private final List<String> validationErrors;

	public BadRequestException(List<String> validationMessages) {
		super("Validation Failed");
		this.validationErrors = validationMessages;
	}

	public BadRequestException(String message) {
		this(Collections.singletonList(message));
	}

	public List<String> getValidationErrors() {
		return validationErrors;
	}

}
