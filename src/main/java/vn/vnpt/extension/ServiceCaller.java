package vn.vnpt.extension;


import lombok.extern.slf4j.Slf4j;
import vn.vnpt.extension.enums.OptionalCall;
import vn.vnpt.extension.model.ApiRequest;
import vn.vnpt.extension.model.ApiResponse;

/**
 * @author Tran Truong
 * @since 31/08/2023
 */
@Slf4j
public class ServiceCaller {

	private Caller caller;

	private ServiceCaller() {
	}

	protected ServiceCaller(Caller caller) {
		this.caller = caller;
	}

	public static ServiceCaller onApi() {
		return new ServiceCaller(new ApiCaller());
	}

	public ServiceCaller optionalCall(OptionalCall optionalCall) {
		this.caller.setOptionalCall(optionalCall);
		return this;
	}

	public ServiceCaller timeOut(int connectionTimeOut) {
		this.caller.setConnectionTimeOut(connectionTimeOut);
		return this;
	}

	public ServiceCaller forwardHeader(boolean forwardHeader) {
		this.caller.setForwardHeader(forwardHeader);
		return this;
	}

	public ApiResponse call(ApiRequest apiRequest) {
		return caller.callApi(apiRequest);
	}

}
