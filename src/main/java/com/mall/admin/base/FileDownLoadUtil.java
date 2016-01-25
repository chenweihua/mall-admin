package com.mall.admin.base;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class FileDownLoadUtil {
	// 下载文件
	public static void downloadCSV(HttpServletResponse response, String fileName, String content) throws Exception {
		if (StringUtils.isBlank(fileName)) {
			return;
		}
		if (!(fileName.toLowerCase().endsWith("csv"))) {
			fileName += ".csv";
		}
		fileName = new String(fileName.getBytes(), "ISO-8859-1"); // 各浏览器基本都支持ISO编码
		// PrintWriter write = null;
		try {
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			// write = new PrintWriter(response.getOutputStream());
			// content = new String(content.getBytes());
			// write.write(content);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(),
					"UTF-16LE"));
			out.write('\ufeff');
			out.write(content);
			out.flush();

			if (out != null) {
				out.close();
			}
		} finally {
			// if (write != null) {
			// write.flush();
			// write.close();
			// }
		}

	}
}
