package com.example.demo.filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import java.util.Optional;
import java.util.UUID;

/**
 * @author meow
 */
@Slf4j
public class RequestFilter extends AbstractRequestLoggingFilter {

    private final String TRACE_ID = "x-trace-id";

    @Override
    protected boolean shouldLog(@NonNull HttpServletRequest request) {
        return true;
    }

    @Override
    protected void beforeRequest(@NonNull HttpServletRequest request, @NonNull String message) {
        // Sleuth header is X-B3-TraceId
        final String traceId = Optional.ofNullable(request.getHeader(TRACE_ID))
                .orElseGet(this::generateTraceId);
        MDC.put(TRACE_ID, traceId);
        log.info(message);
    }

    @Override
    protected void afterRequest(@NonNull HttpServletRequest request, @NonNull String message) {
        log.info(message);
    }

    private String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
