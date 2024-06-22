package vn.hust.common.exception;

import lombok.Getter;

@Getter
class WrapException extends RuntimeException {

	private final String errorCode;

	public WrapException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

}
