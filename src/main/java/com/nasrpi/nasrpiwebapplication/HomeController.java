/* 
 * Copyright nasrpi 2020
 */

package com.nasrpi.nasrpiwebapplication;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;

import io.swagger.annotations.Api;

/*
 * @author zuiee
 */

@RestController
@Api
@RequestMapping
public class HomeController {

	@Autowired
	private StorageHandler storageHandler;
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String sayHello() {
		return ("hello");
	}
	
	@RequestMapping(value = "/localStorage", method = RequestMethod.GET, produces = "application/json")
	public String getStorageData() {
		//String data = StorageHandler.getInstance().queryStorage();
		//JSONObject data = queryStorage();
		JSONObject data = storageHandler.queryStorage();
		
		return data.toString();
	}
	
	public JSONObject queryStorage() {
		String root_path = "E:\\";
		/*StringBuilder files_string = new StringBuilder();
		String [] pathnames;*/
		
		JSONArray jsonFiles = new JSONArray();
		JSONObject jsonData = new JSONObject();
		
		File f = new File(root_path);
		//pathnames = f.list();
		
		File [] files = f.listFiles();
		
		for (File file : files) {
			JSONObject json = new JSONObject();
			if(file.getName().equals("trialfile.txt")) {
				System.out.println(file.delete());
			}
			try {
				json.put("type", file.isDirectory()?"directory" : "file");
				json.put("name", file.getName());
				json.put("path",file.getAbsolutePath());
				
				JSONObject jsonPermissions = new JSONObject();
				jsonPermissions.put("can_execute",file.canExecute());
				jsonPermissions.put("can_read",file.canRead());
				jsonPermissions.put("can_write",file.canWrite());
				json.put("permissions", jsonPermissions);
				
				json.put("size", file.length());
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			jsonFiles.put(json);
		}
		
		jsonData.put("files", jsonFiles);
		return jsonData;
	}

}
