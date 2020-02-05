package com.nasrpi.filesharing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response model for upload API
 * 
 * @author grandolf49
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UploadFileResponseModel {

	private String fileName;

	private String fileDownloadUri;

	private String fileType;

	private long size;

}