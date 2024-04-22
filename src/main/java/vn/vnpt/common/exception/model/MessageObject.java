package vn.vnpt.common.exception.model;

import lombok.Data;

@Data
public class MessageObject {

	private String objectName;
	private String message;

	public MessageObject(String objectName, String message) {
		super();
		this.objectName = objectName;
		this.message = message;
	}


}
