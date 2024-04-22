package vn.vnpt.extension.json;


import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.function.Supplier;

@Slf4j
public class JsonUtils {

	private static final Gson gson = new GsonBuilder().create();

	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}

	public static JsonElement toJsonTree(Object obj) {
		return gson.toJsonTree(obj);
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

	public static <T> T fromJson(JsonElement json, Type type) {
		return gson.fromJson(json, type);
	}

	public static <T> T fromJson(JsonElement json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

	public static void replaceValue(JsonElement json, String key, Number value) {
		if (json instanceof JsonObject jsonObject && jsonObject.has(key)) {
			jsonObject.remove(key);
			jsonObject.addProperty(key, value);
		}
	}

	public static void replaceValue(JsonElement json, String key, String value) {
		if (json instanceof JsonObject jsonObject && jsonObject.has(key)) {
			jsonObject.remove(key);
			jsonObject.addProperty(key, value);
		}
	}

	public static void remove(JsonElement json, String key) {
		if (json instanceof JsonObject jsonObject && jsonObject.has(key)) {
			jsonObject.remove(key);
		}
	}

	public static boolean isValid(String json) {
		try {
			JsonParser.parseString(json);
		} catch (JsonSyntaxException e) {
			return false;
		}
		return true;
	}

	public static <T> T getValue(Supplier<T> supplier, T defaultValue) {
		try {
			return supplier.get();
		} catch (Exception e) {
			log.error("An error occurred while getting value: {}", e.getMessage());
			return defaultValue;
		}
	}

	public static <T> T getValue(Supplier<T> supplier) {
		try {
			return supplier.get();
		} catch (Exception e) {
			log.error("An error occurred while getting value: {}", e.getMessage());
			return null;
		}
	}

}
