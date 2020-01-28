package com.nasrpi.nasrpiwebapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.nasrpi.filesharing.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageProperties.class })
public class NasrpiWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(NasrpiWebApplication.class, args);
	}

}
