package com.nasrpi.nasrpiwebapplication;

import java.io.File;

import io.swagger.annotations.Api;

/*
 * Singleton handler class for interacting with local storage of the NAS.
 * 
 * @author grandolf49
 */

public class StorageHandler {
	private static StorageHandler mStorageHandler = null;
	
	private StorageHandler() {
		if(mStorageHandler == null) {
			mStorageHandler = new StorageHandler();
		}
	}
	
	public static StorageHandler getInstance() {
		return mStorageHandler;
	}
	
	public String queryStorage() {
		StringBuilder files = new StringBuilder();
		String root_path = "E:\\";
		String [] pathnames;
		
		File f = new File(root_path);
		pathnames = f.list();
		
		for (String string : pathnames) {
			files.append(string);
			files.append("\n");
		}
		
		files.append("Hello");
		
		return files.toString();
	}
}
