package vn.vnpt.extension;

import vn.vnpt.extension.enums.OptionalCall;
import vn.vnpt.extension.model.ApiRequest;
import vn.vnpt.extension.model.ApiResponse;

public interface Caller {

	ApiResponse callApi(ApiRequest apiRequest);

	void setConnectionTimeOut(int connectionTimeOut);

	void setForwardHeader(boolean forwardHeader);

	void setOptionalCall(OptionalCall optionalCall);

}
