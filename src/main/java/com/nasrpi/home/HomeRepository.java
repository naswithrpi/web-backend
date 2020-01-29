/* 
 * Copyright nasrpi 2020
 */

package com.nasrpi.home;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nasrpi.common.KeyConstants;
import com.nasrpi.filesharing.FileStorageService;
import com.nasrpi.filesharing.UploadFileResponse;

/**
 * File related operations for Home Controller
 * 
 * @author zuilee
 */

@Repository
public class HomeRepository {

	public List<GetContentsModel> getContents(final String path) {

		List<GetContentsModel> getContentsArray = new ArrayList<GetContentsModel>();

		try {
			Stream<Path> file = Files.list(Paths.get(path)).filter(Files::isRegularFile);
			List<String> fileArray = file.map(x -> x.toString()).collect(Collectors.toList());

			for (String fileName : fileArray) {
				GetContentsModel getContents = new GetContentsModel();
				getContents.setFilePath(fileName);
				getContents.setDirectory(false);

				getContentsArray.add(getContents);
			}

			Stream<Path> directory = Files.list(Paths.get(path)).filter(Files::isDirectory);
			List<String> directoryArray = directory.map(x -> x.toString()).collect(Collectors.toList());

			for (String directoryName : directoryArray) {
				GetContentsModel getContents = new GetContentsModel();
				getContents.setFilePath(directoryName);
				getContents.setDirectory(true);

				getContentsArray.add(getContents);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return getContentsArray;
	}

	public boolean delete(final String path) {

		boolean isFolderDeleted = true;

		try {
			Files.walk(Paths.get(path)).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		} catch (IOException e) {
			e.printStackTrace();
			isFolderDeleted = false;
		}

		return isFolderDeleted;
	}

	public boolean createDirectory(final String directoryPath) {

		Path newDirectoryPath = Paths.get(directoryPath);

		boolean directoryExists = Files.exists(newDirectoryPath);
		if (!directoryExists) {
			try {
				Files.createDirectories(newDirectoryPath);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	public String getFileNameFromPath(String path) {

		int idx = path.lastIndexOf(KeyConstants.DIRECTORY_DELIMITER);

		return path.substring(idx + 1);
	}

	public boolean moveFolder(String source, String destination) {

		boolean isFileMoved = true;

		destination += KeyConstants.DIRECTORY_DELIMITER;
		destination += getFileNameFromPath(source);

		File srcDir = new File(source);
		File destDir = new File(destination);

		try {
			FileUtils.moveDirectory(srcDir, destDir);
		} catch (FileExistsException e) {
			try {
				isFileMoved = true;
				FileUtils.moveDirectory(srcDir, new File(destination + HomeConstants.STRING_FILE_COPY));
			} catch (IOException e1) {
				isFileMoved = false;
				e1.printStackTrace();
			}
		} catch (IOException e) {
			isFileMoved = false;
			e.printStackTrace();
		}

		return isFileMoved;
	}

	public boolean moveFile(String source, String destination) {

		boolean isFileMoved = true;

		File srcDir = new File(source);
		File destDir = new File(destination);

		try {
			FileUtils.moveFileToDirectory(srcDir, destDir, true);
		} catch (FileExistsException e) {
			isFileMoved = true;
			try {
				String fileName = getFileNameFromPath(source);
				FileUtils.moveFile(srcDir, new File(
						destination + KeyConstants.DIRECTORY_DELIMITER + fileName + HomeConstants.STRING_FILE_COPY));
			} catch (IOException e1) {
				isFileMoved = false;
				e1.printStackTrace();
			}
		} catch (IOException e) {
			isFileMoved = false;
			e.printStackTrace();
		}

		return isFileMoved;

	}

	public UploadFileResponse uploadFile(MultipartFile file, String uploadPath, FileStorageService fileStorageService) {

		String fileName = fileStorageService.storeFile(file, uploadPath);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	public ResponseEntity<Resource> downloadFile(String fileName, String filePath, HttpServletRequest request,
			FileStorageService fileStorageService) {
		// Load file as Resource
		Resource resource = fileStorageService.loadFileAsResource(fileName, filePath);

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
