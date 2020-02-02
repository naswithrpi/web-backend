/* 
 * Copyright nasrpi 2020
 */

package com.nasrpi.auth;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import com.nasrpi.common.KeyConstants;

/**
 * File related operations for Auth Controller
 * 
 * @author zuilee
 */

@Repository
public class AuthRepository {
	@Autowired
	private AuthUtils authUtils;
	
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

	
	public boolean login(final String username, final String password) {
		
		//decode password while reading
		try {
			
			List<String> configArray = authUtils.readConfigFile(Paths.get(AuthConstants.IS_CONFIG));
			
			for(String credential : configArray) {
				if(credential.startsWith(username.trim()) && credential.endsWith(password.trim()))
					return true;	//login successful
			}
			return false;	//login failed
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean createPassword(final String username, final String password) {
		
		//encode password while storing
		
		String credential = username.trim() + AuthConstants.WHITE_SPACE + password.trim() + AuthConstants.NEW_LINE;
		List<String> configArray = authUtils.readConfigFile(Paths.get(AuthConstants.IS_CONFIG));
		
		for(String list : configArray) {
			if(list.startsWith(username.trim()))
				return false;	//user name already exists
			}
		
		try {
			OutputStream configStream = Files.newOutputStream(Paths.get(AuthConstants.IS_CONFIG), CREATE, APPEND);
			configStream.write(credential.getBytes());
			return true;
				
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

}
