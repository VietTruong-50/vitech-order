package vn.vnpt.extension;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import vn.vnpt.extension.enums.OptionalCall;
import vn.vnpt.extension.helper.ServiceHelper;
import vn.vnpt.extension.json.JsonUtils;
import vn.vnpt.extension.model.ApiRequest;
import vn.vnpt.extension.model.ApiResponse;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.function.Function;

import static vn.vnpt.extension.enums.OptionalCall.LOAD_BALANCED;
import static vn.vnpt.extension.enums.OptionalCall.REST_API;

@Slf4j
@Setter
public abstract class AbstractCaller implements Caller, ApplicationContextAware {

	private int connectionTimeOut;
	protected boolean forwardHeader;
	private OptionalCall optionalCall = REST_API;
	private ApplicationContext context;

	@Override
	public void setApplicationContext(@NonNull ApplicationContext context) {
		this.context = context;
	}

	protected ApiResponse withRestApi(ApiRequest apiRequest, RestTemplate restTemplate) {
		try {
			if (forwardHeader) forwardHeader(apiRequest);
			String url = apiRequest.getUri().toUriString();
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			String requestBody = gson.toJson(apiRequest.getEntity().getBody());
			HttpMethod method = apiRequest.getMethod() != null ? apiRequest.getMethod() : HttpMethod.POST;
			log.info("[EXTERNAL_REQUEST]: path: {}, requestBody: {}, header: {}", url,
					requestBody.length() > 20000 ? requestBody.substring(0, 20000) : requestBody, apiRequest.getEntity().getHeaders());
			Instant startTime = Instant.now();
			ResponseEntity<byte[]> result = restTemplate.exchange(apiRequest.getUri().toUri(), method, apiRequest.getEntity(), byte[].class);
			log.info("[EXTERNAL_RESPONSE_TIME]: {}", Duration.between(startTime, Instant.now()).toMillis());
			String responseBody = ServiceHelper.supportDecodeGzip(result);
			log.info("[EXTERNAL_RESPONSE]: responseBody: {}", responseBody.length() > 20_000 ? responseBody.substring(0, 1000) : responseBody);
			return new ApiResponse(JsonUtils.fromJson(responseBody, JsonElement.class));
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void forwardHeader(ApiRequest apiRequest) {
		HttpHeaders headersForward = ServiceHelper.buildHeaderInRequest(context.getBean(HttpServletRequest.class));
		HttpHeaders headersOriginal = apiRequest.getEntity().getHeaders();
		if (!headersOriginal.isEmpty()) {
			headersOriginal.toSingleValueMap().forEach(
					(k, v) -> headersForward.put(k, Collections.singletonList(v)));
		}
		apiRequest.setEntity(new HttpEntity<>(apiRequest.getEntity().getBody(), headersForward));
	}

	protected static class RestTemplateCaller {
		private final RestTemplate restTemplate;

		protected RestTemplateCaller(RestTemplate restTemplate) {
			this.restTemplate = restTemplate;
		}

		public <R> R call(Function<RestTemplate, R> function) {
			return function.apply(restTemplate);
		}
	}

	protected RestTemplateCaller restTemplate() {
		return switchRestTemplate();
	}

	private RestTemplateCaller switchRestTemplate() {
		RestTemplate restTemplate;
		switch (optionalCall) {
			case REST_API: {
				log.info("Call api with option mode: {}", REST_API);
				restTemplate = new RestTemplate();
				break;
			}
			case LOAD_BALANCED: {
				log.info("Call api with option mode: {}", LOAD_BALANCED);
				restTemplate = context.getBean("loadBalancedRestTemplate", RestTemplate.class);
				break;
			}
			default: {
				log.info("Call api with option default: {}", REST_API);
				restTemplate = new RestTemplate();
				break;
			}
		}
		if (connectionTimeOut > 0) {
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setConnectTimeout(connectionTimeOut);
			restTemplate.setRequestFactory(requestFactory);
		}
		return new RestTemplateCaller(restTemplate);
	}

}
