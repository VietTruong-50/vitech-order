package vn.vnpt.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.request.async.DeferredResult;
import vn.vnpt.common.constant.ConstantString;
import vn.vnpt.common.errorcode.ErrorCode;
import vn.vnpt.common.exception.IllegalArgumentException;
import vn.vnpt.common.exception.*;
import vn.vnpt.common.response.ResponseEntities;

@Slf4j
public abstract class AbstractResponseController {

	protected AbstractResponseController() {
	}

	public DeferredResult<ResponseEntity<?>> responseEntityDeferredResult(CallbackFunction<?> callbackFunction) {
		DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>(ConstantString.TIMEOUT_NONBLOCK);
		deferredResult.onTimeout(() -> deferredResult.setErrorResult(
				ResponseEntities.createErrorResponse(HttpStatus.REQUEST_TIMEOUT, ErrorCode.IDG_00000408)));
		try {
			deferredResult.setResult(ResponseEntities.createSuccessResponse(HttpStatus.OK, callbackFunction.execute()));
		} catch (NotFoundException ex) {
			log.warn(ex.getMessage());
			deferredResult.setResult(ResponseEntities.createErrorResponse(HttpStatus.NOT_FOUND,
					ex.getErrorCode(), ex.getMessage().trim()));
		} catch (UnauthorizedException ex) {
			deferredResult.setResult(ResponseEntities.createErrorResponse(HttpStatus.UNAUTHORIZED,
					ErrorCode.IDG_00000401, ex.getMessage().trim()));
		} catch (AccessDeniedException ex) {
			log.warn(ex.getMessage());
			deferredResult.setResult(ResponseEntities.createErrorResponse(HttpStatus.NOT_ACCEPTABLE,
					ErrorCode.IDG_00000406, Common.subString(ex.getMessage().trim())));
		} catch (ApiErrorException ex) {
			deferredResult.setResult(ResponseEntities.createErrorResponse(ex.getApiError()));
		} catch (BadRequestException ex) {
			deferredResult.setResult(ResponseEntities.createErrorResponse(HttpStatus.BAD_REQUEST,
					ex.getErrorCode(), Common.subString(ex.getMessage().trim())));
		} catch (IllegalArgumentException ex) {
			deferredResult.setResult(ResponseEntities.createErrorResponse(HttpStatus.BAD_REQUEST,
					ex.getErrorCode(), Common.subString(ex.getMessage().trim())));
		} catch (RuntimeException ex) {
			log.error(ex.getMessage(), ex);
			deferredResult.setResult(ResponseEntities.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorCode.IDG_00000500, Common.subString(ex.getMessage().trim())));
		}
		return deferredResult;
	}


}
