/* 
 * Copyright nasrpi 2020
 */

package com.nasrpi.auth;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.nasrpi.common.KeyConstants;

/**
 * File related operations for Auth Controller
 * 
 * @author zuilee
 */

@Repository
public class AuthRepository {

	public boolean authenticate() {

		try {
			Stream<Path> file = Files.list(Paths.get(KeyConstants.ROOT_PATH)).filter(t -> {
				try {
					return Files.isHidden(t);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			});
			List<String> fileArray = file.map(x -> x.toString()).collect(Collectors.toList());

			if (fileArray.contains(AuthConstants.IS_CONFIG))
				return true;
			else
				return false;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

}
