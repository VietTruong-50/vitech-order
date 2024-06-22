package vn.hust.common.exception;

import lombok.Getter;
import vn.hust.common.errorcode.ErrorCode;

@Getter
public class IllegalArgumentException extends WrapException {

	public IllegalArgumentException(String message) {
		super(message, ErrorCode.IDG_00000400);
	}

	public IllegalArgumentException(String message, String errorCode) {
		super(message, errorCode);
	}
}
