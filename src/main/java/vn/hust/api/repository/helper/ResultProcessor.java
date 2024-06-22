package vn.hust.api.repository.helper;

import lombok.extern.slf4j.Slf4j;
import vn.hust.common.Common;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Slf4j
public class ResultProcessor<T> {

	private static final Map<Class<?>, Class<?>> primitiveClassMap = initPrimitiveClassMap();

	public List<T> process(List<Object[]> resultList, ResultParser<T> parser) {
		List<T> result = new ArrayList<>();
		for (Object[] r : resultList) {
			result.add(parser.parse(r));
		}

		return result;
	}

	public List<T> processRs(ResultSet rs, ResultSetParser<T> parser) {
		List<T> result = new ArrayList<>();
		try {
			while (rs.next()) {
				result.add(parser.parseRs(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("could not process result set");
		} finally {
			try {
				Statement statement = rs.getStatement();
				statement.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public List<?> processRs(ResultSet rs, Class<?> type) {
		List<Object> result = new ArrayList<>();

		try {
			// Get all column labels from metadata
			Set<String> columns = new HashSet<>();
			ResultSetMetaData metaData = rs.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				columns.add(metaData.getColumnLabel(i));
			}

			// Get all field of class type has col name in columns list
			Map<String, Field> fields = new HashMap<>();
			for (Field f : type.getDeclaredFields()) {
				Col col = f.getAnnotation(Col.class);
				if (col != null && (columns.contains(col.value().toLowerCase())
						|| columns.contains(col.value().toUpperCase()))) {
					fields.put(col.value(), f);
				}
			}

			while (rs.next()) {
				Object obj;
				try {
					obj = type.getConstructor().newInstance();
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("could not init obj");
				}

				for (Map.Entry<String, Field> entry : fields.entrySet()) {
					String col = entry.getKey();
					Field f = entry.getValue();
					try {
						Class<?> t = f.getType();
						if (t.isPrimitive()) {
							t = getPrimitiveClass(t);
						}

						Object resFromDb = null;
						Class<?> rsType = rs.getObject(col) == null ? null : rs.getObject(col).getClass();
						if (rsType == BigDecimal.class) {
							resFromDb = rs.getObject(col, rsType);
							if (resFromDb != null) {
								f.setAccessible(true);
								f.set(obj, Common.converterBigDecimal(t, resFromDb));
							}
						} else if (rsType != null) {
							resFromDb = rs.getObject(col, t);
							if (resFromDb != null) {
								f.setAccessible(true);
								f.set(obj, resFromDb);
							}
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
						throw new RuntimeException("could not set obj");
					}
				}

				result.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("could not process result set");
		} finally {
			try {
				Statement statement = rs.getStatement();
				statement.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private Class<?> getPrimitiveClass(Class<?> primitive) {
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

		return map;
	}
}
