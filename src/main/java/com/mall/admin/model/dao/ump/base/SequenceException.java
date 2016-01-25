package com.mall.admin.model.dao.ump.base;

/**
 * @author 有天
 */
public class SequenceException extends Exception {
	private static final long serialVersionUID = 1L;

	public SequenceException() {
		super();
	}

	public SequenceException(String message) {
		super(message);
	}

	public SequenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public SequenceException(Throwable cause) {
		super(cause);
	}
}