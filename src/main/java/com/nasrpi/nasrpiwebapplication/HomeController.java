/* 
 * Copyright nasrpi 2020
 */

package com.nasrpi.nasrpiwebapplication;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private StorageHandler storageHandler;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String sayHello() {
		return ("hello");
	}

	@RequestMapping(value = "/getAllRootItems", method = RequestMethod.GET, produces = "application/json")
	public String getAllRootItems() {
		JSONObject data = storageHandler.getAllRootItems();
		if (data != null) {
			return data.toString();
		} else {
			return "{ \"error\" : \"No external device found\"}";
		}
	}
}
