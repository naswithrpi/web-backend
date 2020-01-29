/*
 * Copyright nasrpi 2020
 */

package com.nasrpi.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nasrpi.common.KeyConstants;
import com.nasrpi.filesharing.FileStorageService;
import com.nasrpi.filesharing.UploadFileResponse;

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

	@RequestMapping(value = "/moveFolder", method = RequestMethod.POST, produces = KeyConstants.APPLICATION_JSON)
	public boolean moveFolder(@RequestBody final MoveModel moveModel) {
		return homeRepository.moveFolder(moveModel.getSource(), moveModel.getDestination());
	}

	@RequestMapping(value = "/moveFile", method = RequestMethod.POST, produces = KeyConstants.APPLICATION_JSON)
	public boolean moveFile(@RequestBody final MoveModel moveModel) {
		return homeRepository.moveFile(moveModel.getSource(), moveModel.getDestination());
	}

	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("path") final String uploadPath) {
		return homeRepository.uploadFile(file, uploadPath, fileStorageService);
	}

	@GetMapping("/downloadFile")
	public ResponseEntity<Resource> downloadFile(@RequestParam("path") final String filePath,
			@RequestParam("fileName") final String fileName, HttpServletRequest request) {
		return homeRepository.downloadFile(fileName, filePath, request, fileStorageService);
	}

}
