package vn.hust.common.exception;

import lombok.Getter;
import vn.hust.common.exception.model.ApiError;

@Getter
public class ApiErrorException extends RuntimeException {

	private final ApiError apiError;

	public ApiErrorException(ApiError apiError) {
		super(apiError.getMessage());
		this.apiError = apiError;
	}

}
