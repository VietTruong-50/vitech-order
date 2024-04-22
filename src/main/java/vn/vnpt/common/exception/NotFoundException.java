package vn.vnpt.common.exception;

import vn.vnpt.common.errorcode.ErrorCode;

public class NotFoundException extends WrapException {

	public NotFoundException(String message) {
		super(message, ErrorCode.IDG_00000404);
	}

	public NotFoundException(String message, String errorCode) {
		super(message, errorCode);
	}

}
