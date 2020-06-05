package ru.kpfu.itis.logging;

import ru.kpfu.itis.settings.LoggingSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpPerformanceMetricsInterceptor implements AsyncHandlerInterceptor {

    private final ThreadLocal<Long> executionTime = new ThreadLocal<>();

    private final QueryCountInterceptor queryCountInterceptor;

    private final LoggingSettings loggingSettings;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (loggingSettings.getShowRequestExecutionTime()) {
            executionTime.set(System.currentTimeMillis());
        }
        if (loggingSettings.getShowQueryCount()) {
            queryCountInterceptor.startCounter();
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {

        Boolean showRequestExecutionTime = loggingSettings.getShowRequestExecutionTime();
        Boolean showQueryCount = loggingSettings.getShowQueryCount();

        StringBuilder messageBuilder = new StringBuilder();

        if (showRequestExecutionTime || showQueryCount) {
            messageBuilder.append(String.format(
                    "Request %s %s processed",
                    request.getMethod(),
                    request.getRequestURI()
            ));

            if (showRequestExecutionTime) {
                long totalMillis = System.currentTimeMillis() - executionTime.get();
                executionTime.remove();
                messageBuilder.append(String.format(
                        " in %d ms",
                        totalMillis
                ));
            }
            if (showQueryCount) {
                Long queryCount = queryCountInterceptor.getQueryCount();
                queryCountInterceptor.clearCounter();
                messageBuilder.append(String.format(
                        " with %d queries",
                        queryCount
                ));
            }
            log.debug(messageBuilder.toString());
        }
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response,
                                               Object handler) {
        if (loggingSettings.getShowRequestExecutionTime()) {
            executionTime.remove();
        }
        if (loggingSettings.getShowQueryCount()) {
            queryCountInterceptor.clearCounter();
        }
    }
}
