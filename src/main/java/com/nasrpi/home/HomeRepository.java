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

import org.springframework.stereotype.Repository;

/**
 * File related operations for Home Controller
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

}
