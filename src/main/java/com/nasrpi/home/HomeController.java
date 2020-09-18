/*
 * Copyright nasrpi 2020
 */

package com.nasrpi.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nasrpi.common.KeyConstants;
import com.nasrpi.filesharing.FileStorageService;
import com.nasrpi.filesharing.UploadFileResponseModel;

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

	@Autowired
	private FileStorageService fileStorageService;

	@RequestMapping(value = "/getAllRootItems", method = RequestMethod.GET, produces = KeyConstants.APPLICATION_JSON)
	public List<GetContentsModel> getAllRootItems() {
		return homeRepository.getContents(KeyConstants.ROOT_PATH);
	}

	@RequestMapping(value = "/getContents", method = RequestMethod.POST, produces = KeyConstants.APPLICATION_JSON)
	public List<GetContentsModel> getContents(@RequestBody final String path) {
		return homeRepository.getContents(path);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = KeyConstants.APPLICATION_JSON)
	public boolean delete(@RequestBody final String path) {
		return homeRepository.delete(path);
	}

	@RequestMapping(value = "/createDirectory", method = RequestMethod.POST)
	public boolean createDirectory(@RequestBody final String directoryPath) {
		return homeRepository.createDirectory(directoryPath);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public List<GetContentsModel> search(@RequestBody final SearchModel searchModel) {
		return homeRepository.searchInCurrentDirectory(searchModel);
	}

	@RequestMapping(value = "/moveFolder", method = RequestMethod.POST, produces = KeyConstants.APPLICATION_JSON)
	public boolean moveFolder(@RequestBody final MoveModel moveModel) {
		return homeRepository.moveFolder(moveModel.getSource(), moveModel.getDestination());
	}

	@RequestMapping(value = "/moveFile", method = RequestMethod.POST, produces = KeyConstants.APPLICATION_JSON)
	public boolean moveFile(@RequestBody final MoveModel moveModel) {
		return homeRepository.moveFile(moveModel.getSource(), moveModel.getDestination());
	}
	
	@RequestMapping(value = "/getSpaceUsage", method = RequestMethod.GET, produces = "application/json")
	public List<GetSpaceModel> getSpaceUsage() {
		return homeRepository.getSpaceUsage(KeyConstants.ROOT_PATH);
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = KeyConstants.APPLICATION_JSON)
	public UploadFileResponseModel uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("path") final String uploadPath) {
		return homeRepository.uploadFile(file, uploadPath, fileStorageService);
	}

	@RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
	public ResponseEntity<Resource> downloadFile(@RequestBody final DownloadFileModel downloadFileModel,
			HttpServletRequest request) {
		return homeRepository.downloadFile(downloadFileModel.getFileName(), downloadFileModel.getFilePath(), request,
				fileStorageService);
	}
	
	@RequestMapping(value="/getUserActivity", method = RequestMethod.GET)
	public List<UserActivityModel> getUserActivity() {
		return homeRepository.getActivity();
	}
	
	@RequestMapping(value="/updateUserActivity", method = RequestMethod.POST)
	public boolean updateUserActivity(@RequestBody final UserActivityModel userActivityModel) {
		return homeRepository.updateActivity(userActivityModel);
	}

}
