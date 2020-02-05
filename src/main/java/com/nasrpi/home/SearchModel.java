/* 
 * Copyright nasrpi 2020
 */

package com.nasrpi.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request Model for /search api call
 * 
 * @author zuilee
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchModel {

	private String searchKey;

	private String currentPath;

}
