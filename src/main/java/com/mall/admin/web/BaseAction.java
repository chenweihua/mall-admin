package com.mall.admin.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

import com.mall.admin.util._;

public class BaseAction {
	
	/**
     * js侧datatable需要的返回数据格式
     * @param draw 序列号
     * @param recordsTotal 总数
     * @param recordsFiltered 满足条件的总数
     * @param data 数据
     * @return
     */
    public static Map<String, Object> buildDataTableResult(int draw, long recordsTotal, long recordsFiltered, List data) {
        return _.asMap("draw", draw, "recordsTotal", recordsTotal, "recordsFiltered", recordsFiltered, 
                "data", data);
    }
    
    /**
     * js侧datatable需要的返回数据格式，错误消息
     * @param draw 序列号
     * @param error 错误信息
     * @return
     */
    public static Map<String, Object> buildDataTableError(int draw, String error) {
        return _.asMap("draw", draw, "recordsTotal", 0, "recordsFiltered", 0, 
                "data", null, "error", error);
    }
    
    public String getDataFromRequest(HttpServletRequest request) throws IOException {
    	return IOUtils.toString(request.getInputStream());
	}

}
