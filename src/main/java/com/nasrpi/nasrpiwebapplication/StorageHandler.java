package com.nasrpi.nasrpiwebapplication;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/*
 * Handler class for interacting with local storage of the NAS.
 * 
 * @author grandolf49
 */

@Component
public class StorageHandler {

	public JSONObject getAllRootItems() {
		String root_path = "E:\\";

		JSONArray jsonFiles = new JSONArray();
		JSONObject jsonData = new JSONObject();

		File f = new File(root_path);

		File[] files = f.listFiles();

		if (files != null) {
			for (File file : files) {
				JSONObject json = new JSONObject();

				try {
					json.put("type", file.isDirectory() ? "directory" : "file");
					json.put("name", file.getName());
					json.put("path", file.getAbsolutePath());

					JSONObject jsonPermissions = new JSONObject();
					jsonPermissions.put("can_execute", file.canExecute());
					jsonPermissions.put("can_read", file.canRead());
					jsonPermissions.put("can_write", file.canWrite());
					json.put("permissions", jsonPermissions);

					json.put("size", file.length());

				} catch (JSONException e) {
					e.printStackTrace();
				}

				jsonFiles.put(json);
			}

			jsonData.put("files", jsonFiles);
			return jsonData;
		} else {
			return null;
		}

	}
}
