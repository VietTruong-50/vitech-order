package vn.vnpt.common.model;

import lombok.Data;

@Data
public class ApiResult<T> {

	private String message;

	private T object;

	public ApiResult(String message, T object) {
		super();
		this.message = message;
		this.object = object;
	}

	public ApiResult() {
		super();
	}
}
