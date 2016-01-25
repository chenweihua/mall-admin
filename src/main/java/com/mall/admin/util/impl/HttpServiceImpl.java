package com.mall.admin.util.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.UnsupportedCharsetException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import com.mall.admin.constant.LogConstant;
import com.mall.admin.exception.RemoteInvocationFailureException;
import com.mall.admin.util.HttpService;
import com.mall.admin.vo.HttpParamDto;

@Service
public class HttpServiceImpl implements HttpService {

	@Override
	public String sendRequest(String url, int connectTimeout, int readTimeout,
			String charset, boolean returnSingle)
			throws RemoteInvocationFailureException {
		BufferedReader in = null;
		HttpURLConnection conn = null;
		try {
			if (StringUtils.isBlank(charset)) {
				charset = HttpParamDto.DEFAULT_CHARSET;
			}
			conn = getURLConnection(url, connectTimeout, readTimeout);
			in = new BufferedReader(new InputStreamReader(connect(conn),
					charset));

			String result = null;
			result = this.getReturnResult(in, returnSingle);
			if (StringUtils.isBlank(result)) {
				throw new RemoteInvocationFailureException("网络异常，" + url
						+ " 返回数据为空");
			}
			return result;
		} catch (IOException e) {
			LogConstant.mallLog.error("", e);
			throw new RemoteInvocationFailureException("网络IO异常[" + url + "]", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				LogConstant.mallLog.error("", e);
			}
		}
	}

	@Override
	public String sendRequest(String url)
			throws RemoteInvocationFailureException {
		return this.sendRequest(url, HttpParamDto.DEFAULT_CONNECT_TIME_OUT,
				HttpParamDto.DEFAULT_READ_TIME_OUT,
				HttpParamDto.DEFAULT_CHARSET, false);
	}
	
	@Override
	public String sendRequest(String url, String content, Header[] headers)
			throws org.springframework.remoting.RemoteInvocationFailureException {
		try {
			HttpPost post = new HttpPost(url);
			
			post.setEntity(new StringEntity(content,"UTF-8"));
			for( Header h : headers ) {
				post.setHeader(h);
			}
			HttpRequestBase request = post;
 
			// 设置POST的请求数据(封装成JSON格式)
 
			HttpClient httpClient = HttpClientBuilder.create().build();
			// 执行请求
			HttpResponse response = httpClient.execute(request);
 
			HttpEntity entity = response.getEntity();
			InputStream is = null;
			try {
			    if (entity != null) {
			        is = entity.getContent();
			        return IOUtils.toString(is, "UTF-8");
			    }
			    return null;
			} catch (Exception e) {
			    e.printStackTrace();
			} finally {
			    if (is != null) {
			        is.close();
			    }
			}
		} catch (UnsupportedCharsetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String sendRequest(String url, boolean returnSingle)
			throws RemoteInvocationFailureException {
		return this.sendRequest(url, HttpParamDto.DEFAULT_CONNECT_TIME_OUT,
				HttpParamDto.DEFAULT_READ_TIME_OUT,
				HttpParamDto.DEFAULT_CHARSET, returnSingle);
	}

	public String sendPostRequest(String url, String content, String charset,
			int connectTimeout, int readTimeout, boolean needCompress,
			String contentType, boolean returnSingle)
			throws RemoteInvocationFailureException {
		BufferedReader in = null;
		HttpURLConnection httpConn = null;
		try {
			httpConn = getURLConnection(url, connectTimeout, readTimeout,
					contentType);
			if (StringUtils.isBlank(charset)) {
				charset = HttpParamDto.DEFAULT_CHARSET;
			}
			InputStream stream = postConnect(httpConn, content, charset,
					needCompress);

			in = new BufferedReader(new InputStreamReader(stream, charset));
			String result = getReturnResult(in, returnSingle);
			// LogConstant.mallLog.debug("请求返回结果:" + result);
			if (StringUtils.isBlank(result)) {
				throw new RemoteInvocationFailureException("网络异常，" + url
						+ "无法联通");
			}
			return result;
		} catch (IOException e) {
			LogConstant.mallLog.error("", e);
			throw new RemoteInvocationFailureException("网络IO异常[" + url + "]", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				// if (httpConn != null)
				// {
				// httpConn.disconnect();
				// }
			} catch (IOException e) {
				LogConstant.mallLog.error("", e);
			}
		}
	}

	@Override
	public String sendPostRequest(String url, String content, String charset) {
		return sendPostRequest(url, content, charset,
				HttpParamDto.DEFAULT_CONNECT_TIME_OUT,
				HttpParamDto.DEFAULT_READ_TIME_OUT);
	}

	@Override
	public String sendPostRequest(String url, String content, String charset,
			int connectTimeout, int readTimeout)
			throws RemoteInvocationFailureException {
		return sendPostRequest(url, content, charset, connectTimeout,
				readTimeout, false);
	}

	@Override
	public String sendPostRequest(String url, String content, String charset,
			boolean needCompress) throws RemoteInvocationFailureException {
		return sendPostRequest(url, content, charset,
				HttpParamDto.DEFAULT_CONNECT_TIME_OUT,
				HttpParamDto.DEFAULT_READ_TIME_OUT, needCompress);
	}

	@Override
	public String sendPostRequest(String url, String content, String charset,
			int connectTimeout, int readTimeout, String contentType)
			throws RemoteInvocationFailureException {
		return sendPostRequest(url, content, charset, connectTimeout,
				readTimeout, false, contentType, false);
	}

	@Override
	public String sendPostRequest(String url, String content, String charset,
			int connectTimeout, int readTimeout, boolean needCompress)
			throws RemoteInvocationFailureException {
		return sendPostRequest(url, content, charset, connectTimeout,
				readTimeout, needCompress, null, false);
	}

	/**
	 * post方式发送请求
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
	@Override
	public String sendPostRequest(String url, String content, String charset,
			int connectTimeout, int readTimeout, boolean needCompress,
			SSLContext sslContext) throws MalformedURLException, IOException {
		InputStream is = null;
		// BufferedReader in = null;
		HttpURLConnection httpConn = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		charset = StringUtils.isBlank(charset) ? HttpParamDto.DEFAULT_CHARSET
				: charset;
		try {
			httpConn = getURLConnection(url, connectTimeout, readTimeout, null);
			if (StringUtils.isBlank(charset)) {
				charset = HttpParamDto.DEFAULT_CHARSET;
			}
			is = postConnect(httpConn, content, charset, needCompress);
			int ch;
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(baos);
			while ((ch = bis.read()) != -1) {
				bos.write(ch);
			}
			bos.flush();
			bis.close();
			return new String(baos.toByteArray(), HttpParamDto.DEFAULT_CHARSET);
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (httpConn != null) {
					httpConn.disconnect();
				}
			} catch (Exception e) {
				throw new IOException("[连接关闭异常]", e);
			}
		}
	}

	@Override
	public String sendHttpsPostRequest(String url, String content,
			String charset, int connectTimeout, int readTimeout,
			boolean needCompress, String contentType, boolean returnSingle)
			throws RemoteInvocationFailureException {
		BufferedReader in = null;
		HttpsURLConnection httpsConn = null;
		try {
			httpsConn = getHttpsURLConnection(url, connectTimeout, readTimeout,
					contentType);
			if (StringUtils.isBlank(charset)) {
				charset = HttpParamDto.DEFAULT_CHARSET;
			}
			InputStream stream = postConnect(httpsConn, content, charset,
					needCompress);

			in = new BufferedReader(new InputStreamReader(stream, charset));
			String result = getReturnResult(in, returnSingle);
			if (StringUtils.isBlank(result)) {
				throw new RemoteInvocationFailureException("网络异常，" + url
						+ "无法联通");
			}
			return result;
		} catch (IOException e) {
			LogConstant.mallLog.error("", e);
			throw new RemoteInvocationFailureException("网络IO异常[" + url + "]", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				LogConstant.mallLog.error("", e);
			}
		}
	}

	@Override
	public String sendHttpsPostRequest(String url, String content,
			String charset) {
		return sendHttpsPostRequest(url, content, charset,
				HttpParamDto.DEFAULT_CONNECT_TIME_OUT,
				HttpParamDto.DEFAULT_READ_TIME_OUT, false, null, false);
	}

	private InputStream postConnect(HttpURLConnection httpConn, String content,
			String charset, boolean needCompress) {
		String urlStr = httpConn.getURL().toString();
		try {
			if (StringUtils.isBlank(charset)) {
				charset = HttpParamDto.DEFAULT_CHARSET;
			}
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true,
			// 默认情况下是false;
			httpConn.setDoOutput(true);
			// Post 请求不能使用缓存
			httpConn.setUseCaches(false);
			// 设定请求的方法为"POST"，默认是GET
			httpConn.setRequestMethod("POST");
			if (needCompress) {
				sendCompressRequest(content, charset, httpConn);
			} else {
				sendNoCompressRequest(content, charset, httpConn);
			}
			// 接收数据
			if (needCompress) {
				return new GZIPInputStream(httpConn.getInputStream());
			}
			return httpConn.getInputStream();
		} catch (MalformedURLException e) {
			LogConstant.mallLog.error("", e);
			throw new RemoteInvocationFailureException(
					"远程访问异常[" + urlStr + "]", e);
		} catch (IOException e) {
			LogConstant.mallLog.error("", e);
			throw new RemoteInvocationFailureException(
					"网络IO异常[" + urlStr + "]", e);
		}
	}

	private void sendCompressRequest(String content, String charset,
			HttpURLConnection httpConn) {
		GZIPOutputStream out = null;
		try {
			httpConn.setRequestProperty("Content-Type", "application/x-gzip");
			httpConn.setRequestProperty("Accept", "application/x-gzip");
			out = new GZIPOutputStream(httpConn.getOutputStream());
			out.write(content.getBytes("GBK"));
			out.flush();
		} catch (IOException e) {
			LogConstant.mallLog.error("", e);
			throw new RemoteInvocationFailureException("网络IO异常["
					+ httpConn.getURL().toString() + "]", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LogConstant.mallLog.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 发送原始消息
	 * 
	 * @param content
	 * @param charset
	 * @param httpConn
	 */
	private void sendNoCompressRequest(String content, String charset,
			HttpURLConnection httpConn) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new OutputStreamWriter(
					httpConn.getOutputStream(), charset));
			out.write(content);
			out.flush();
		} catch (IOException e) {
			LogConstant.mallLog.error("", e);
			throw new RemoteInvocationFailureException("网络IO异常["
					+ httpConn.getURL().toString() + "]", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 建立远程连接
	 * 
	 * @return
	 */
	private InputStream connect(HttpURLConnection httpConn) {
		String urlStr = httpConn.getURL().toString();
		try {
			if (httpConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				LogConstant.mallLog.error(urlStr + "|ResponseCode="
						+ httpConn.getResponseCode());
				throw new RemoteInvocationFailureException("远程访问" + urlStr
						+ "出错，返回结果为：" + httpConn.getResponseCode());
			}
			return httpConn.getInputStream();
		} catch (IOException e) {
			LogConstant.mallLog.error("", e);
			throw new RemoteInvocationFailureException(
					"网络IO异常[" + urlStr + "]", e);
		}
	}

	/**
	 * 构造URLConnnection
	 * 
	 * @param urlStr
	 * @param connectTimeout
	 * @param readTimeout
	 * @param contentType
	 *            content-type类型
	 * @return
	 * @throws RemoteInvocationFailureException
	 */
	private HttpURLConnection getURLConnection(String urlStr,
			int connectTimeout, int readTimeout, String contentType)
			throws RemoteInvocationFailureException {
		try {
			URL remoteUrl = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection) remoteUrl
					.openConnection();
			httpConn.setConnectTimeout(connectTimeout);
			httpConn.setReadTimeout(readTimeout);
			if (contentType != null) {
				httpConn.setRequestProperty("content-type", contentType);
			}
			return httpConn;
		} catch (MalformedURLException e) {
			LogConstant.mallLog.error("", e);
			throw new RemoteInvocationFailureException(
					"远程访问异常[" + urlStr + "]", e);
		} catch (IOException e) {
			LogConstant.mallLog.error("", e);
			throw new RemoteInvocationFailureException(
					"网络IO异常[" + urlStr + "]", e);
		}
	}

	/**
	 * 构造URLConnnection
	 *
	 * @param urlStr
	 * @param connectTimeout
	 * @param readTimeout
	 * @param contentType
	 *            content-type类型
	 * @return
	 * @throws RemoteInvocationFailureException
	 */
	private HttpsURLConnection getHttpsURLConnection(String urlStr,
			int connectTimeout, int readTimeout, String contentType)
			throws RemoteInvocationFailureException {
		try {
			URL remoteUrl = new URL(urlStr);
			HttpsURLConnection httpConn = (HttpsURLConnection) remoteUrl
					.openConnection();
			httpConn.setConnectTimeout(connectTimeout);
			httpConn.setReadTimeout(readTimeout);
			if (contentType != null) {
				httpConn.setRequestProperty("content-type", contentType);
			}
			return httpConn;
		} catch (MalformedURLException e) {
			LogConstant.mallLog.error("", e);
			throw new RemoteInvocationFailureException(
					"远程访问异常[" + urlStr + "]", e);
		} catch (IOException e) {
			LogConstant.mallLog.error("", e);
			throw new RemoteInvocationFailureException(
					"网络IO异常[" + urlStr + "]", e);
		}
	}

	private HttpURLConnection getURLConnection(String urlStr,
			int connectTimeout, int readTimeout)
			throws RemoteInvocationFailureException {
		return getURLConnection(urlStr, connectTimeout, readTimeout, null);
	}

	private String getReturnResult(BufferedReader in, boolean returnSingleLine)
			throws IOException {
		if (returnSingleLine) {
			return in.readLine();
		} else {
			StringBuffer sb = new StringBuffer();
			String result = "";
			while ((result = in.readLine()) != null) {
				LogConstant.mallLog.debug("从中心返回：" + result);
				sb.append(StringUtils.trimToEmpty(result));
			}
			return sb.toString();
		}
	}

	public static void main(String avgs[]) {
		String content = "{\"userId\":542}";
		String ret = new HttpServiceImpl().sendPostRequest("http://uctest.imxiaomai.com/user/getById", content, "utf-8");
		System.out.println(ret);
	}
}
