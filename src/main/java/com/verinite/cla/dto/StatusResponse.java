package com.verinite.cla.dto;

public class StatusResponse {

	private String status;

	private Integer code;

	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public StatusResponse(String status, Integer code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public StatusResponse() {
	}

}
