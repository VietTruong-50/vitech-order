package vn.hust.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.hust.common.errorcode.ErrorCode;
import vn.hust.common.exception.model.ApiError;
import vn.hust.common.model.ApiResult;

public class ResponseEntities {

	public static <T> ResponseEntity<?> createSuccessResponse(HttpStatus status, T data) {
		ApiResult<?> result = new ApiResult<>(ErrorCode.Success, data);
		return new ResponseEntity<>(result, status);
	}

	public static ResponseEntity<?> createErrorResponse(HttpStatus status, final String errorMessage) {
		final ApiError apiError = new ApiError(status, errorMessage);
		return new ResponseEntity<>(apiError, status);
	}

	public static ResponseEntity<?> createErrorResponse(final HttpStatus status, final String errorMessage, final String responseData) {
		final ApiError apiError = new ApiError(status, errorMessage, responseData);
		return new ResponseEntity<>(apiError, status);
	}

	public static ResponseEntity<?> createErrorResponse(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}