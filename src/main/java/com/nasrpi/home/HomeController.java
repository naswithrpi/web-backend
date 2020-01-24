/*
 * Copyright nasrpi 2020
 */

package com.nasrpi.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nasrpi.common.KeyConstants;

import io.swagger.annotations.Api;

/**
 * Controller to consume Home page api endpoints
 * 
 * @author zuilee
 */

@RestController
@Api
@RequestMapping
public class HomeController {

	@Autowired
	private HomeRepository homeRepository;

	@RequestMapping(value = "/getAllRootItems", method = RequestMethod.GET, produces = "application/json")
	public List<GetContentsModel> getAllRootItems() {
		return homeRepository.getContents(KeyConstants.ROOT_PATH);
	}

	@RequestMapping(value = "/getContents", method = RequestMethod.POST, produces = "application/json")
	public List<GetContentsModel> getContents(@RequestBody final String path) {
		return homeRepository.getContents(path);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	public boolean delete(@RequestBody final String path) {
		return homeRepository.delete(path);
	}

	@RequestMapping(value = "/createDirectory", method = RequestMethod.POST)
	public boolean createDirectory(@RequestBody final String directoryPath) {
		return homeRepository.createDirectory(directoryPath);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public List<GetContentsModel> search(final String searchKey, final String currentPath) {
		return homeRepository.searchInCurrentDirectory(searchKey, currentPath);
	}

	@RequestMapping(value = "/moveFolder", method = RequestMethod.POST, produces = "application/json")
	public boolean moveFolder(@RequestBody final MoveModel moveModel) {
		return homeRepository.moveFolder(moveModel.getSource(), moveModel.getDestination());
	}
	
	@RequestMapping(value = "/moveFile", method = RequestMethod.POST, produces = "application/json")
	public boolean moveFile(@RequestBody final MoveModel moveModel) {
		return homeRepository.moveFile(moveModel.getSource(), moveModel.getDestination());
	}

}
