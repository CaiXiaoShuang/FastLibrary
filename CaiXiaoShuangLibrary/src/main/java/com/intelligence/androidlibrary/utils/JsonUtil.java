package com.intelligence.androidlibrary.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Json转换工具
 */
public class JsonUtil {


	public static Object jsonToObject(String json, Class<?> classOfT) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson.fromJson(json, classOfT);
	}

	public static Object jsonToObject(String json, Type type) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson.fromJson(json, type);
	}

	public static List<?> jsonToList(String json, TypeToken<?> token) {
		return (List<?>) jsonToObject(json, token.getType());
	}

	public static String objetcToJson(Object object) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson.toJson(object);
	}
}

