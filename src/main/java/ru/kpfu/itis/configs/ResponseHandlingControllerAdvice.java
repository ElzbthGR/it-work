package ru.kpfu.itis.configs;

import ru.kpfu.itis.dto.Response;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Nonnull;
import java.util.Optional;

@ControllerAdvice(annotations = RestController.class, assignableTypes = ExceptionHandlingControllerAdvice.class)
public class ResponseHandlingControllerAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(@Nonnull MethodParameter returnType, @Nonnull Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @Nonnull MethodParameter returnType,
                                  @Nonnull MediaType selectedContentType, @Nonnull Class selectedConverterType,
                                  @Nonnull ServerHttpRequest request, @Nonnull ServerHttpResponse response) {
        if (body instanceof Response) {
            Response responseBody = ((Response) body);
            Optional.ofNullable(responseBody.getStatus())
                    .ifPresent(response::setStatusCode);
            Optional.ofNullable(responseBody.getHeaders())
                    .ifPresent(headers -> response.getHeaders().putAll(headers));
        }
        return body;
    }
}
