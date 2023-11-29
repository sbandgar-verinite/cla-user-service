package com.verinite.cla.service;

import org.apache.coyote.BadRequestException;

import com.verinite.cla.dto.StatusResponse;

public interface ConfigService {

	StatusResponse addConfiguration(String key, Object value) throws BadRequestException;

	Object getConfiguration(String key) throws BadRequestException;

}
