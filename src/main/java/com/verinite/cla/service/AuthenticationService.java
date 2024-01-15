package com.verinite.cla.service;

import java.io.IOException;

import com.verinite.cla.dto.JwtAuthenticationResponse;
import com.verinite.cla.dto.SignUpRequest;
import com.verinite.cla.dto.SigninRequest;
import com.verinite.cla.dto.StatusResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    StatusResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request, HttpServletResponse response) throws IOException;
}