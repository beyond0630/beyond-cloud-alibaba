package com.beyond.cloud.alibaba.business.config;

import java.util.Collection;

import com.beyond.cloud.alibaba.common.ApiResult;
import com.beyond.cloud.alibaba.exception.ApiException;
import com.beyond.cloud.alibaba.utils.JsonUtils;
import feign.Feign;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Configuration
public class FeignErrorDecoder implements ErrorDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeignErrorDecoder.class);

    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

    @Override
    public Exception decode(final String methodKey, final Response response) {
        LOGGER.debug("MethodKey: {}", methodKey);
        LOGGER.debug("Status: {}", response.status());
        LOGGER.debug("Reason: {}", response.reason());
        LOGGER.debug("Headers: {}", response.headers());

        Response.Body body = response.body();
        if (body == null) {
            return new ApiException("服务端响应：" + response.status());
        }
        String responseBody = body.toString();
        LOGGER.debug("Body: {}", responseBody);

        final String contentType = this.getHeader(response, HttpHeaders.CONTENT_TYPE);
        if (contentType == null) {
            return new ApiException("服务端响应：" + response.status());
        } else if (!contentType.toLowerCase().startsWith(MediaType.APPLICATION_JSON_VALUE)) {
            return new ApiException(response.body().toString());
        }

        ApiResult ApiResult = JsonUtils.deserialize(responseBody, ApiResult.class);
        return new ApiException(ApiResult.getCode(), ApiResult.getMessage());
    }

    private String getHeader(final Response response, final String name) {
        final Collection<String> values = response.headers().get(name);
        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.stream().findFirst().orElse(null);
    }

}
