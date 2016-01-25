package com.mall.admin.exception;

/**
 * 配制未发现的异常
 * 
 * @author xliao
 * 
 */
@SuppressWarnings("serial")
public class SystemConfigNotFoundException extends RuntimeException
{

	public SystemConfigNotFoundException()
	{
		super();
	}

	public SystemConfigNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public SystemConfigNotFoundException(String message)
	{
		super(message);
	}

	public SystemConfigNotFoundException(Throwable cause)
	{
		super(cause);
	}

}
