package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.dto.ApplicationDto;
import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.dto.TenantDto;
import com.verinite.cla.dto.UserDto;
import com.verinite.cla.service.ApplicationService;
import com.verinite.cla.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    @PostMapping("/tenant/add")
    public ResponseEntity<StatusResponse> createTenant(@RequestBody TenantDto tenantDto) {
        // Tenant createTenant = applicationService.createTenant(tenantDto);
        StatusResponse createTenant = applicationService.createTenant(tenantDto);
        return new ResponseEntity<>(createTenant, HttpStatus.CREATED);
    }

//    @GetMapping("/tenant/get/all")
//    public ResponseEntity<List<TenantDto>> getAllTenants() {
//        return ResponseEntity.ok(applicationService.getAllTenant());
//    }

    @GetMapping("/tenant/get/all")
    public ResponseEntity<List<TenantDto>> getAllTenants(@RequestParam(name = "application_number", required = false) String applicationNumber) {
        return ResponseEntity.ok(applicationService.getAllTenant(applicationNumber));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> allUser = userService.getAllUser();
        return new ResponseEntity<List<UserDto>>(allUser, HttpStatus.OK);
    }

    @PostMapping("/tenant/onboard")
    public ResponseEntity<StatusResponse> onboardTenant(@RequestBody ApplicationDto applicationDto) {
        StatusResponse statusReponse = applicationService.onboardTenant(applicationDto);
        return new ResponseEntity<>(statusReponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<StatusResponse> createApplication(@RequestBody ApplicationDto applicationDto) {
        StatusResponse applicationDto1 = applicationService.createApplication(applicationDto);
        return new ResponseEntity<>(applicationDto1, HttpStatus.CREATED);
    }

    @GetMapping("/{applicationNumber}/details")
    public ResponseEntity<ApplicationDto> getApplicationDetails(@PathVariable String applicationNumber) {
        ApplicationDto applicationDetails = applicationService.getApplicationDetails(applicationNumber);
        return new ResponseEntity<>(applicationDetails, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<ApplicationDto>> getAllApplication() {
        List<ApplicationDto> applicationDetails = applicationService.getAllApplicationDetails();
        return new ResponseEntity<>(applicationDetails, HttpStatus.OK);
    }

//    @PatchMapping("/tenant/status")
//    public ResponseEntity<Tenant> updateTenantStatus(@RequestParam(name = "id") Integer id,
//                                                     @RequestParam(name = "status") String status) {
//        Tenant updateTenantStatus = applicationService.updateTenantStatus(id, status);
//        return new ResponseEntity<Tenant>(updateTenantStatus, HttpStatus.OK);
//    }
@PatchMapping("/tenant/status")
public ResponseEntity<TenantDto> updateTenantStatus(@RequestParam(name = "tenant_code") String tenantCode,
                                                    @RequestParam(name = "status") String status) {

    TenantDto updateTenantStatus = applicationService.updateTenantStatus(tenantCode, status);
    return new ResponseEntity<TenantDto>(updateTenantStatus, HttpStatus.OK);
}

    @PatchMapping("/{applicationNumber}/status")
    public ResponseEntity<ApplicationDto> updateApplictionStatus(@PathVariable String applicationNumber,
                                                                 @RequestParam(name = "status") String status) {

        ApplicationDto applicationDto = applicationService.updateApplicationStatus(applicationNumber, status);

        return new ResponseEntity<>(applicationDto, HttpStatus.OK);
    }

    @GetMapping("/tenant/details")
    public ResponseEntity<TenantDto> getTenantDetails(@RequestParam(name = "tenant_code") String tenantCode) {
        TenantDto tenantDetails = applicationService.getTenantDetails(tenantCode);
        return new ResponseEntity<TenantDto>(tenantDetails, HttpStatus.OK);
    }

    @PostMapping("/tenant/user")
    public ResponseEntity<StatusResponse> onboardUser(@RequestBody TenantDto applicationDto) {
        StatusResponse statusReponse = applicationService.onboardUser(applicationDto);
        return new ResponseEntity<>(statusReponse, HttpStatus.OK);
    }

    @GetMapping("/userDetails")
    public ResponseEntity<UserDto> getUserDetails(@RequestHeader String email) {
        UserDto userDetails = userService.getUserDetails(email);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }
}
