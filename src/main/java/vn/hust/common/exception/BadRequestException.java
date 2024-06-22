package vn.hust.common.exception;

import lombok.Getter;
import vn.hust.common.errorcode.ErrorCode;

@Getter
public class BadRequestException extends WrapException {

	public BadRequestException(String message) {
		super(message, ErrorCode.IDG_00000400);
	}

	public BadRequestException(String message, String errorCode) {
		super(message, errorCode);
	}
}
