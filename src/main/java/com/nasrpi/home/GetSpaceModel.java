/* 
 * Copyright nasrpi 2020
 */

package com.nasrpi.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response Model for /getContents api call
 * @author zuilee
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class GetSpaceModel {
	
	private long totalSpace;
	
	private long usedSpace;
	
	private long freeSpace;	

}
