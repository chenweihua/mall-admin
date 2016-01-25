package com.mall.admin.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class StreamUtil {

	/* ------------------------- consume ------------------------- */

	public static byte[] consume(InputStream in) throws IOException {
		if (in == null)
			return null;
		ByteArrayOutputStream out = new ByteArrayOutputStream(bufferSize);
		byte[] buffer = new byte[bufferSize];
		while (true) {
			int size = in.read(buffer);
			if (size <= 0)
				break;
			out.write(buffer, 0, size);
		}
		in.close();
		out.close();
		return out.toByteArray();
	}

	public static String consume(Reader in) throws IOException {
		if (in == null)
			return null;
		StringBuilder out = new StringBuilder(bufferSize);
		char[] buffer = new char[bufferSize];
		int read;
		while ((read = in.read(buffer)) > 0)
			out.append(buffer, 0, read);
		in.close();
		return out.toString();
	}
	
	 /**
     * @param imgUrl 图片地址
     * @return 
     */ 
    public static BufferedImage getBufferedImage(String imgUrl) { 
        URL url = null; 
        InputStream in = null; 
        BufferedImage imgBuffer = null; 
        try { 
            url = new URL(imgUrl); 
            in = url.openStream(); 
            imgBuffer = ImageIO.read(in); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally { 
            try { 
                in.close(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
        return imgBuffer; 
    } 

	// 虽然大多数时候是在处理网页，但也有可能处理很少的内容，不到1k不算太浪费
	// 供参考，PipedInputStream的buffer是1k
	private static final int bufferSize = 512;

	/* ------------------------- close ------------------------- */

	public static void safeClose(InputStream in) {
		if (in != null)
			try {
				in.close();
			} catch (IOException e) {
			}
	}
	public static void safeClose(Reader in) {
		if (in != null)
			try {
				in.close();
			} catch (IOException e) {
			}
	}

}
