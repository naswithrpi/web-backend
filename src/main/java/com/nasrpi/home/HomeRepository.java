/* 
 * Copyright nasrpi 2020
 */

package com.nasrpi.home;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;

import com.nasrpi.common.KeyConstants;

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

	public List<GetSpaceModel> getSpaceUsage(String rootPath) {
		
		List<GetSpaceModel> getSpaceArray = new ArrayList<GetSpaceModel>();
		GetSpaceModel getSpace = new GetSpaceModel();
		File freeSpace =  new File(rootPath);
		
		try {
		getSpace.setTotalSpace(freeSpace.getTotalSpace());
		getSpace.setFreeSpace(freeSpace.getFreeSpace());
		getSpace.setUsedSpace(getSpace.getTotalSpace() - getSpace.getFreeSpace());
		
		getSpaceArray.add(getSpace);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return getSpaceArray;
	}

}