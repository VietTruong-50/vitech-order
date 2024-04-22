package vn.vnpt.extension;

import vn.vnpt.extension.model.ApiRequest;
import vn.vnpt.extension.model.ApiResponse;

public class ApiCaller extends AbstractCaller implements Caller {

	@Override
	public ApiResponse callApi(ApiRequest apiRequest) {
		return restTemplate()
				.call(restTemplate -> withRestApi(apiRequest, restTemplate));
	}

}
