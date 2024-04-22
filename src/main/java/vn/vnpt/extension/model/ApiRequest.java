package vn.vnpt.extension.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponents;

import java.time.Duration;

@Getter
@Builder
public class ApiRequest {

	private UriComponents uri;

	@Setter
	@Builder.Default
	private HttpEntity<?> entity = HttpEntity.EMPTY;

	private Duration connectionTimeout;

	private Duration readTimeout;

	private HttpMethod method;

}
