package com.verinite.cla.controlleradvice;

import java.util.Collections;
import java.util.List;

public class ForbiddenException extends RuntimeException {

	private final List<String> validationErrors;

	public ForbiddenException(List<String> validationMessages) {
		this.validationErrors = validationMessages;
	}

	public ForbiddenException(String message) {
		this(Collections.singletonList(message));
	}

	public List<String> getValidationErrors() {
		return validationErrors;
	}

}
