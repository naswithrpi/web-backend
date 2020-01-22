package com.nasrpi.home;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

@Repository
public class HomeRepository {

	public List<String> getContents(final String path) {
		
		List<GetContentsModel> getContentsList = new ArrayList<GetContentsModel>();

		List<String> result = new ArrayList<>();

		try (Stream<Path> walk = Files.list(Paths.get(path))) {
			
			GetContentsModel getContentsModel = new GetContentsModel();
			
			getContentsModel.setFilePath(walk.toString());
			System.out.println("model : " + getContentsModel.getFilePath());

			//result = (walk.filter(Files::isRegularFile)).map(x -> x.toString()).collect(Collectors.toList());
			
//			result.addAll(walk.filter(Files::isDirectory)
//					.map(x -> x.toString()).collect(Collectors.toList()));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
