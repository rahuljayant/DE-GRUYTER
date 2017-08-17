package org.mps.degruyter.exception;

/**
 ****************************************************************************************
 * Created By : Siddhartha Mahakur
 * Created Date : April 27,2017
 * Last Modified By : Siddhartha Mahakur
 * Last Modified Date: 
 ****************************************************************************************
 *
 */
public class FTPConnectionException extends Exception {
	private static final long serialVersionUID = -5472143221389292211L;

	public FTPConnectionException() {
	}

	public FTPConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public FTPConnectionException(String message) {
		super(message);
	}

	public FTPConnectionException(Throwable cause) {
		super(cause);
	}
}
