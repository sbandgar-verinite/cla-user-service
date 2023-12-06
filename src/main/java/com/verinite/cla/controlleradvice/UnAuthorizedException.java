package com.verinite.cla.controlleradvice;

import java.util.Collections;
import java.util.List;

public class UnAuthorizedException extends RuntimeException {

	private final List<String> validationErrors;

	public UnAuthorizedException(List<String> validationMessages) {
		this.validationErrors = validationMessages;
	}

	public UnAuthorizedException(String message) {
		this(Collections.singletonList(message));
	}

	public List<String> getValidationErrors() {
		return validationErrors;
	}

}
