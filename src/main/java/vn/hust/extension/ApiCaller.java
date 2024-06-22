package vn.hust.extension;

import vn.hust.extension.model.ApiRequest;
import vn.hust.extension.model.ApiResponse;

public class ApiCaller extends AbstractCaller implements Caller {

	@Override
	public ApiResponse callApi(ApiRequest apiRequest) {
		return restTemplate()
				.call(restTemplate -> withRestApi(apiRequest, restTemplate));
	}

}
