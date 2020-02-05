/* 
 * Copyright nasrpi 2020
 */

package com.nasrpi.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response Model for /login and /createPassword api call
 * @author monica
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginModel {
	
	private String username;
	
	private String password;

}
