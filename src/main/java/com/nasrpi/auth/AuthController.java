/*
 * Copyright nasrpi 2020
 */

package com.nasrpi.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * Controller to consume Login page api endpoints
 * 
 * @author zuilee
 */

@RestController
@Api
@RequestMapping
public class AuthController {

	@Autowired
	private AuthRepository authRepository;

	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public boolean authenticate() {
		return authRepository.authenticate();
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public boolean login(@RequestBody final LoginModel loginModel) {
		return authRepository.login(loginModel.getUsername(), loginModel.getPassword());
	}
	
	@RequestMapping(value = "/createPassword", method = RequestMethod.POST)
	public boolean createPassword(@RequestBody final LoginModel loginModel) {
		return authRepository.createPassword(loginModel.getUsername(), loginModel.getPassword());
	}
	
}
