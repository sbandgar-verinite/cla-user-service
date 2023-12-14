package com.verinite.cla.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TenantInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantId = request.getHeader("tenantId");

        if (tenantId == null || tenantId.isEmpty()) {
            // If tenantId is missing or empty, you can choose to handle it by sending an error response or redirecting
            // For simplicity, we'll throw an exception in this example
            throw new IllegalArgumentException("Tenant ID is required in the header");
        }

        // Set the tenantId as an attribute in the request for later use in the controllers
        request.setAttribute("tenantId", tenantId);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        // Do nothing here
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
        // Do nothing here
    }
}
