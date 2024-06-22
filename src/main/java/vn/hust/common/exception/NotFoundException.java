package vn.hust.common.exception;

import vn.hust.common.errorcode.ErrorCode;

public class NotFoundException extends WrapException {

	public NotFoundException(String message) {
		super(message, ErrorCode.IDG_00000404);
	}

	public NotFoundException(String message, String errorCode) {
		super(message, errorCode);
	}

}
