/* 
 * Copyright nasrpi 2020
 */

package com.nasrpi.auth;

/**
 * Utility methods for Auth
 * 
 * @author monica
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;


@Service
public final class AuthUtils {
	 
	public List<String> readConfigFile(Path path){
		
		List<String> configArray = new ArrayList<String>();
		
		try {
		Stream<String> configCredentials = Files.lines(path).distinct();
		configArray = configCredentials.map(x -> x.toString()).collect(Collectors.toList());
		
		} catch(IOException e) {
			e.printStackTrace();
		}
		return configArray;
	}

}
