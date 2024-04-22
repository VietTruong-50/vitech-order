package vn.vnpt.common.exception.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import vn.vnpt.common.errorcode.ErrorCode;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiError {
	private List<MessageObject> messageObjects;
	private List<MessageField> messageFields;
	private String statusCode;
	private String message;
	private HttpStatus status;
	private String error;

	public static ApiError fieldExist(String messageDb) {
		String field = messageDb.split(StringUtils.SPACE)[0];
		String[] fields = field.split(",");
		List<MessageField> listMessageFields = new ArrayList<>();
		for (String s : fields) {
			listMessageFields.add(new MessageField(s, ErrorCode.FieldExist));
		}
		return new ApiError(listMessageFields, HttpStatus.BAD_REQUEST);
	}

	public ApiError(List<MessageField> messageFields, HttpStatus status) {
		this.messageObjects = new ArrayList<>();
		this.messageFields = messageFields;
		this.message = "";
		this.status = status;
		this.statusCode = status.toString();
		this.error = "";
	}

	public ApiError(List<MessageField> messageFields, List<MessageObject> messageObjects, HttpStatus status) {
		super();
		this.messageObjects = messageObjects;
		this.messageFields = messageFields;
		this.message = "";
		this.status = status;
		this.statusCode = status.toString();
		this.error = "";
	}

	public ApiError(HttpStatus status, String message, List<MessageField> messageFields, List<MessageObject> messageObjects) {
		this.messageObjects = messageObjects;
		this.messageFields = messageFields;
		this.message = message;
		this.statusCode = status.toString();
		this.status = status;
		this.error = "";
	}

	public ApiError(HttpStatus status, String message) {
		this.messageObjects = new ArrayList<>();
		this.messageFields = new ArrayList<>();
		this.message = message;
		this.statusCode = status.toString();
		this.status = status;
		this.error = "";
	}

	public ApiError(HttpStatus status, String message, String error) {
		this.messageObjects = new ArrayList<>();
		this.messageFields = new ArrayList<>();
		this.message = message;
		this.statusCode = status.toString();
		this.status = status;
		this.error = error;
	}
}
