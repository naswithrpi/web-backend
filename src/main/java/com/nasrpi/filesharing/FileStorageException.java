package com.nasrpi.filesharing;

/**
 * Exception class for FileStorageException
 * 
 * @author grandlf49
 */
public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = 6269449526952575984L;

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}