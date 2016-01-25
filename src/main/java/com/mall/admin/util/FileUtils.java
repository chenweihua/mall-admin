package com.mall.admin.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class FileUtils {

	public static List<String[]> readCsv(InputStream in) {
		List<String[]> data = new ArrayList<String[]>();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in, "GBK"));
			// 读取第一行数据，获得条数据有多少个属性
			String content = reader.readLine();
			if (content == null) {
				return null;
			}
			int rowNum = content.split(",").length;

			while ((content = reader.readLine()) != null) {
				String[] rowData = new String[rowNum];
				for (int i = 0; i < rowData.length; i++) {
					rowData[i] = "";
				}
				// 必须包含","的时候才解析数据
				if (content.trim().indexOf(",") >= 0) {
					String[] tempData = content.split(",");
					if (tempData.length > 0) {
						for (int i = 0; i < tempData.length && i < rowData.length; i++) {
							rowData[i] = tempData[i];
						}
						data.add(rowData);
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}

	public static List<String> readCsvForSingleLine(InputStream in) {
		List<String> data = new ArrayList<String>();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in, "GBK"));

			String content = null;

			while ((content = reader.readLine()) != null) {
				data.add(content);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}

	public static void downloadCSV(HttpServletResponse response, String fileName, String content) throws Exception {
		if (StringUtils.isBlank(fileName)) {
			return;
		}
		if (!(fileName.toLowerCase().endsWith("csv"))) {
			fileName += ".csv";
		}
		fileName = new String(fileName.getBytes(), "UTF-8"); // 各浏览器基本都支持ISO编码
		try {
			response.reset();
			response.setContentType("multipart/form-data charset=UTF-8");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(),
					"UTF-16LE"));
			out.write('\ufeff');
			out.write(content);
			out.flush();

			if (out != null) {
				out.close();
			}
		} finally {

		}

	}

}
