package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.verinite.cla.dto.ApplicationDto;
import com.verinite.cla.dto.RoleDto;
import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.dto.TenantDto;
import com.verinite.cla.model.Application;
import com.verinite.cla.model.Tenant;
import com.verinite.cla.model.User;
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
    public ResponseEntity<TenantDto> createTenant(@RequestBody TenantDto tenantDto) {
        // Tenant createTenant = applicationService.createTenant(tenantDto);
        TenantDto createTenant = applicationService.createTenant(tenantDto);
        return new ResponseEntity<>(createTenant, HttpStatus.CREATED);
    }

    @GetMapping("/tenant/get/all")
    public ResponseEntity<List<TenantDto>> getAllTenants() {
       
        return ResponseEntity.ok(applicationService.getAllTenant());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUser = userService.getAllUser();
        System.out.println(allUser);
        return new ResponseEntity<List<User>>(allUser, HttpStatus.OK);
    }

    @PostMapping("/tenant/onboard")
    public ResponseEntity<StatusResponse> onboardTenant(@RequestBody ApplicationDto applicationDto) {
        StatusResponse statusReponse = applicationService.onboardTenant(applicationDto);
        return new ResponseEntity<>(statusReponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApplicationDto> createApplication(@RequestBody ApplicationDto applicationDto) {
        ApplicationDto applicationDto1 = applicationService.createApplication(applicationDto);
        return new ResponseEntity<>(applicationDto1, HttpStatus.CREATED);
    }

    @GetMapping("/details")
    public ResponseEntity<List<ApplicationDto>> getAllApplication(@RequestParam(name = "application_number", required = false) String applicationNumber) {
        List<ApplicationDto> allApplication = applicationService.getAllApplication(applicationNumber);
        return new ResponseEntity<>(allApplication, HttpStatus.OK);
    }


    @PatchMapping("/application/tenant/status")
    public ResponseEntity<Tenant> updateTenantStatus(@RequestParam(name = "id") Integer id,
                                                     @RequestParam(name = "status") String status) {
        Tenant updateTenantStatus = applicationService.updateTenantStatus(id, status);
        return new ResponseEntity<Tenant>(updateTenantStatus, HttpStatus.OK);
    }


    @PatchMapping("/{applicationNumber}/status")
    public ResponseEntity<ApplicationDto> updateApplictionStatus(@PathVariable String applicationNumber, @RequestParam(name = "status") String status) {

        ApplicationDto applicationDto = applicationService.updateApplicationStatus(applicationNumber, status);

        return new ResponseEntity<>(applicationDto, HttpStatus.OK);

    }

    @GetMapping("/application/tenant/details")
    public ResponseEntity<Tenant> getTenantDetails(@RequestParam(name = "id") Integer id) {
        Tenant tenantDetails = applicationService.getTenantDetails(id);
        return new ResponseEntity<Tenant>(tenantDetails, HttpStatus.OK);
    }

	@PostMapping("/tenant/user")
	public ResponseEntity<StatusResponse> mapApplicationTenantUser(@RequestBody ApplicationDto applicationDto) {
		StatusResponse statusReponse = applicationService.mapApplicationTenantUser(applicationDto);
		return new ResponseEntity<>(statusReponse, HttpStatus.OK);
	}
}
