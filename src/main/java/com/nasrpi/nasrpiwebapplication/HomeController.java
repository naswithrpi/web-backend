/* 
 * Copyright nasrpi 2020
 */

package com.nasrpi.nasrpiwebapplication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/*
 * @author zuiee
 */

@RestController
@Api
@RequestMapping
public class HomeController {

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String sayHello() {
		return ("hello");
	}
}
