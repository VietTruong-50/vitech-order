package vn.vnpt.common.exception;

import lombok.Getter;
import vn.vnpt.common.errorcode.ErrorCode;

@Getter
public class IllegalArgumentException extends WrapException {

	public IllegalArgumentException(String message) {
		super(message, ErrorCode.IDG_00000400);
	}

	public IllegalArgumentException(String message, String errorCode) {
		super(message, errorCode);
	}
}
