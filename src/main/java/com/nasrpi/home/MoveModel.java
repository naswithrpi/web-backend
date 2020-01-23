/* 
 * Copyright nasrpi 2020
 */

package com.nasrpi.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

/**
 * Request Model for /move api call
 * 
 * @author grandolf49
 */
public class MoveModel {

	private String source;

	private String destination;

}
