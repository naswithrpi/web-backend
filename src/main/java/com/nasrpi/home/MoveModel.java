/* 
 * Copyright nasrpi 2020
 */

package com.nasrpi.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request Model for /move api call
 * 
 * @author grandolf49
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveModel {

	private String source;

	private String destination;

}
