package com.verinite.cla.service;

import com.verinite.cla.dto.JwtAuthenticationResponse;
import com.verinite.cla.dto.SignUpRequest;
import com.verinite.cla.dto.SigninRequest;
import com.verinite.cla.dto.StatusResponse;

public interface AuthenticationService {
    StatusResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}