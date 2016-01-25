package com.mall.admin.exception;

public class RemoteInvocationFailureException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2252021089986642034L;

	public RemoteInvocationFailureException() {
		super();
	}

	public RemoteInvocationFailureException(String message) {
		super(message);
	}

	public RemoteInvocationFailureException(Throwable cause) {
		super(cause);
	}

	public RemoteInvocationFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
