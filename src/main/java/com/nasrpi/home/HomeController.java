/*
 * Copyright nasrpi 2020
 */

package com.nasrpi.home;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

	@RequestMapping(value = "/moveFolder", method = RequestMethod.POST, produces = "application/json")
	public boolean moveFolder(@RequestBody final MoveModel moveModel) {
		return homeRepository.moveFolder(moveModel.getSource(), moveModel.getDestination());
	}

	@RequestMapping(value = "/moveFile", method = RequestMethod.POST, produces = "application/json")
	public boolean moveFile(@RequestBody final MoveModel moveModel) {
		return homeRepository.moveFile(moveModel.getSource(), moveModel.getDestination());
	}

	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
		String fileName = fileStorageService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = fileStorageService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
