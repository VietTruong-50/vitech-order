package vn.hust.extension;

import vn.hust.extension.enums.OptionalCall;
import vn.hust.extension.model.ApiRequest;
import vn.hust.extension.model.ApiResponse;

public interface Caller {

	ApiResponse callApi(ApiRequest apiRequest);

	void setConnectionTimeOut(int connectionTimeOut);

	void setForwardHeader(boolean forwardHeader);

	void setOptionalCall(OptionalCall optionalCall);

}
