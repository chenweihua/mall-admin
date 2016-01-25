package com.mall.admin.util;

import java.io.IOException;
import java.io.Writer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	public static final ObjectMapper mapper = new ObjectMapper();

	public static String format(Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}

	public static void format(Object obj, Writer out) throws IOException {
		mapper.writeValue(out, obj);
	}

	public static JsonNode parse(String json) throws IOException {
		return mapper.readTree(json);
	}

	public static <T> T parse(String json, TypeReference reference) throws IOException {
		return mapper.readValue(json, reference);
	}

	public static <T> T parse(String json, Class<T> clazz) throws IOException {
		return mapper.readValue(json, clazz);
	}

	/**
	 * 解析json如code/msg/data中的某值
	 * 
	 * @param json
	 * @param name
	 * @param clazz
	 * @param flags
	 *                : 级联名称
	 * @return 格式错误返回null
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static <T> T parse(String json, Class<T> clazz, String... flags) throws IOException {
		JsonNode node = parse(json);
		if (node == null) {
			throw new IOException();
		}
		JsonNode valueNode = node;
		for (String flag : flags) {
			valueNode = valueNode.get(flag);
			if (valueNode == null) {
				throw new IOException();
			}
		}
		return mapper.readValue(valueNode.toString(), clazz);
	}

	/**
	 * 级联解析
	 * 
	 * @param json
	 * @param flags
	 * @return
	 * @throws IOException
	 */
	public static JsonNode parse(String json, String... flags) throws IOException {
		JsonNode node = parse(json);
		if (node == null) {
			throw new IOException();
		}
		JsonNode valueNode = node;
		for (String flag : flags) {
			valueNode = valueNode.get(flag);
			if (valueNode == null) {
				throw new IOException();
			}
		}
		return valueNode;
	}
}
