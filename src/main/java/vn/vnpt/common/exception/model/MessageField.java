package vn.vnpt.common.exception.model;

import lombok.Data;

@Data
public class MessageField {
	private String fieldName;
	private String message;

	public MessageField(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

}
