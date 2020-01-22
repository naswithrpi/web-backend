/* 
 * Copyright nasrpi 2020
 */

package com.nasrpi.home;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
	private HomeRepository homeRepository;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String sayHello() {
		return ("hello");
	}

	@RequestMapping(value = "/getAllRootItems", method = RequestMethod.GET, produces = "application/json")
	public List<String> getAllRootItems() {
		return homeRepository.getContents("D:\\");
	}

	@RequestMapping(value = "/getContents", method = RequestMethod.POST, produces = "application/json")
	public List<String> getContents(@RequestBody final String path) {
		return homeRepository.getContents(path);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	public boolean delete(@RequestBody final String path) {
		return homeRepository.delete(path);
	}

}
