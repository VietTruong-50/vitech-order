package vn.vnpt.extension.constant;

import java.util.HashMap;
import java.util.Map;

public class ConstantPrimitive {

	private static final Map<Class<?>, Class<?>> primitiveClassMap = initPrimitiveClassMap();

	public static Class<?> getPrimitiveClass(Class<?> primitive) {
		return primitiveClassMap.get(primitive);
	}

	private static Map<Class<?>, Class<?>> initPrimitiveClassMap() {
		Map<Class<?>, Class<?>> map = new HashMap<>();
		map.put(short.class, Short.class);
		map.put(int.class, Integer.class);
		map.put(long.class, Long.class);
		map.put(float.class, Float.class);
		map.put(double.class, Double.class);
		map.put(boolean.class, Boolean.class);
		map.put(byte.class, Byte.class);
		map.put(char.class, Character.class);
		map.put(void.class, Void.class);
		map.put(Short.class, short.class);
		map.put(Integer.class, int.class);
		map.put(Long.class, long.class);
		map.put(Float.class, float.class);
		map.put(Double.class, double.class);
		map.put(Boolean.class, boolean.class);
		map.put(Byte.class, byte.class);
		map.put(Character.class, char.class);
		map.put(Void.class, void.class);
		return map;
	}

}
