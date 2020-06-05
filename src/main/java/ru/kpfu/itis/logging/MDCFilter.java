package ru.kpfu.itis.logging;

import ru.kpfu.itis.security.CurrentUser;
import ru.kpfu.itis.settings.LoggingSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class MDCFilter extends HttpFilter {

    private final LoggingSettings loggingSettings;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (loggingSettings.getShowMetadata()) {
            String sessionId = UUID.randomUUID().toString();
            MDC.put("SID", sessionId);
            getOptionalUserId()
                    .ifPresent(userId -> MDC.put("UID", userId));
        }
        chain.doFilter(request, response);
    }

    private Optional<String> getOptionalUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        CurrentUser currentUser = (CurrentUser) securityContext.getAuthentication().getDetails();
        return Optional.ofNullable(currentUser.getId())
                .map(Objects::toString);
    }
}
