package com.nasrpi.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetContentsModel {
	
	private String fileTye;
	
	private String filePath;

}
