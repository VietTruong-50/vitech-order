package vn.vnpt.api.repository.helper;

import jakarta.persistence.ParameterMode;
import lombok.Data;


@Data
public class ProcedureParameter {
	private String name;
	private Class<?> type;
	private ParameterMode parameterMode;
	private Object value;

	private ProcedureParameter() {}

	public static ProcedureParameter inputParam(String name, Class<?> type, Object value) {
		ProcedureParameter parameter = new ProcedureParameter();
		parameter.setName(name);
		parameter.setType(type);
		parameter.setParameterMode(ParameterMode.IN);
		parameter.setValue(value);
		return parameter;
	}

	public static ProcedureParameter outputParam(String name, Class<?> type) {
		ProcedureParameter parameter = new ProcedureParameter();
		parameter.setName(name);
		parameter.setType(type);
		parameter.setParameterMode(ParameterMode.OUT);
		return parameter;
	}

	public static ProcedureParameter refCursorParam(String name) {
		ProcedureParameter parameter = new ProcedureParameter();
		parameter.setName(name);
		parameter.setType(Class.class);
		parameter.setParameterMode(ParameterMode.REF_CURSOR);
		return parameter;
	}
}
