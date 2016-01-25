package com.mall.admin.util;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.springframework.remoting.RemoteInvocationFailureException;

public interface HttpService {

	/**
	 * 发送远程调用请求，返回响应结果
	 * 
	 * @param url
	 * @param connectTimeout
	 *            连接超时时间
	 * @param readTimeout
	 *            读取超时时间
	 * @param charset
	 *            返回结果编码的字符集
	 * @param returnSingleLine
	 *            是否返回单行
	 * @return
	 * @throws RemoteInvocationFailureException
	 *             远程调用异常
	 */
	public String sendRequest(String url, int connectTimeout, int readTimeout,
			String charset, boolean returnSingleLine)
			throws RemoteInvocationFailureException;

	/**
	 * 发送远程调用请求，返回响应结果（默认连接10秒超时，读取10秒 超时，用UTF-8编码）
	 * 
	 * @param url
	 * @return
	 * @throws RemoteInvocationFailureException
	 *             远程调用异常
	 */
	public String sendRequest(String url, boolean returnSingleLine)
			throws RemoteInvocationFailureException;
	
	public String sendRequest(String url, String content, Header[] headers)
			throws RemoteInvocationFailureException;

	/**
	 * 发送远程调用请求，返回响应结果（默认连接10秒超时，读取10秒 超时，用UTF-8编码）
	 * 
	 * @param url
	 * @return
	 * @throws RemoteInvocationFailureException
	 *             远程调用异常
	 */
	public String sendRequest(String url)
			throws RemoteInvocationFailureException;

	/**
	 * 使用POST方式发送远程调用请求，返回响应的全部结果
	 * 
	 * @param url
	 * @param charset
	 * @return
	 * @throws RemoteInvocationFailureException
	 */
	public String sendPostRequest(String url, String content, String charset)
			throws RemoteInvocationFailureException;

	/**
	 * Post发送HTTPS 远程调用请求，返回响应的全部结果
	 * 
	 * @param url
	 * @param content
	 * @param charset
	 * @return
	 * @throws RemoteInvocationFailureException
	 */
	public String sendHttpsPostRequest(String url, String content,
			String charset, int connectTimeout, int readTimeout,
			boolean needCompress, String contentType, boolean returnSingle)
			throws RemoteInvocationFailureException;

	/**
	 * Post发送HTTPS 远程调用请求，返回响应的全部结果
	 * 
	 * @param url
	 * @param content
	 * @param charset
	 * @return
	 * @throws RemoteInvocationFailureException
	 */
	public String sendHttpsPostRequest(String url, String content,
			String charset);

	/**
	 * 使用POST方式发送远程调用请求，返回响应的全部结果(自定义超时时间)
	 * 
	 * @param url
	 * @param connectTimeout
	 * @param readTimeout
	 * @param charset
	 * @return
	 * @throws RemoteInvocationFailureException
	 */
	public String sendPostRequest(String url, String content, String charset,
			int connectTimeout, int readTimeout)
			throws RemoteInvocationFailureException;

	/**
	 * 使用POST方式发送远程调用请求，返回响应的全部结果(自定义超时时间，可选是否要压缩数据发送)
	 * 
	 * @param url
	 * @param content
	 * @param charset
	 * @param connectTimeout
	 * @param readTimeout
	 * @param needCompress
	 *            --是否要压缩数据
	 * @return
	 * @throws RemoteInvocationFailureException
	 */
	public String sendPostRequest(String url, String content, String charset,
			int connectTimeout, int readTimeout, boolean needCompress)
			throws RemoteInvocationFailureException;

	public String sendPostRequest(String url, String content, String charset,
			boolean needCompress) throws RemoteInvocationFailureException;

	public String sendPostRequest(String url, String content, String charset,
			int connectTimeout, int readTimeout, String contentType)
			throws RemoteInvocationFailureException;

	/**
	 * post方式发送请求。
	 * 
	 * @param url
	 * @param content
	 * @param connectTimeout
	 * @param readTimeout
	 * @param needCompress
	 * @param sslContext
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public String sendPostRequest(String url, String content, String charset,
			int connectTimeout, int readTimeout, boolean needCompress,
			SSLContext sslContext) throws MalformedURLException, IOException;

}