package com.cloud.gateway.component.handler;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.RateLimiterErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

/**
 * Created by Junhe on 2018/8/8
 *
 * 限流降级处理
 */
public class ZuulRateLimiterErrorHandler {
    private Logger LOGGER = LoggerFactory.getLogger(ZuulRateLimiterErrorHandler.class);
    @Bean
    public RateLimiterErrorHandler rateLimitErrorHandler() {
        return new DefaultRateLimiterErrorHandler() {
            @Override
            public void handleSaveError(String key, Exception e) {
                LOGGER.error("保存key:[{}]异常", key, e);
            }

            @Override
            public void handleFetchError(String key, Exception e) {
                LOGGER.error("路由失败:[{}]异常", key);
            }

            @Override
            public void handleError(String msg, Exception e) {
                LOGGER.error("限流异常:[{}]", msg, e);
            }
        };
    }
}
