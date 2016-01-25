package com.mall.admin.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GsonUtil {
	
	public static String jsonFormatter(String uglyJSONString){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJSONString);
        String prettyJsonString = gson.toJson(je);
        return prettyJsonString;
    }
	
	public static String toJsonString(Object o){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //JsonParser jp = new JsonParser();
        //JsonElement je = jp.parse(uglyJSONString);
        String prettyJsonString = gson.toJson(o);
        return prettyJsonString;
    }

}
