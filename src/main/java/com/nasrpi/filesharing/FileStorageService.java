package com.nasrpi.filesharing;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * A service class that provides upload and download file service
 * 
 * @author grandolf49
 */

@Service
public class FileStorageService {

	public String storeFile(MultipartFile file, String uploadPath) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {

			Path targetLocation = Paths.get(uploadPath).resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;

		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}

	}

	public Resource loadFileAsResource(String fileName, String downloadFilePath) {

		try {
			Path filePath = Paths.get(downloadFilePath).resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}

		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}
}