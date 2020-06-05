package ru.kpfu.itis.configs;

import org.springframework.security.access.AccessDeniedException;
import ru.kpfu.itis.dto.Response;
import ru.kpfu.itis.exceptions.BadRequestException;
import ru.kpfu.itis.exceptions.ForbiddenException;
import ru.kpfu.itis.exceptions.NotFoundException;
import ru.kpfu.itis.exceptions.TooManyRequestsException;
import ru.kpfu.itis.exceptions.ValidationCollectorException;
import ru.kpfu.itis.exceptions.ValidationException;
import ru.kpfu.itis.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.debug("{}, cause: {}", exception.toString(), ExceptionUtils.getShortStackTrace(exception));
        return getValidationErrorResponse(exception.getLocalizedMessage(), exception.getBindingResult());
    }

    @ExceptionHandler({BindException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response validationExceptionHandler(BindException exception) {
        log.debug("{}, cause: {}", exception.toString(), ExceptionUtils.getShortStackTrace(exception));
        return getValidationErrorResponse(exception.getLocalizedMessage(), exception.getBindingResult());
    }

    private Response getValidationErrorResponse(String localizedMessage, BindingResult bindingResult) {
        return Response.badRequest(
                Response.Error.builder()
                        .message(localizedMessage)
                        .type(Response.Error.Type.VALIDATION_ERROR)
                        .details(bindingResult
                                .getFieldErrors()
                                .stream()
                                .map(fieldError -> Response.Error.Detail.builder()
                                        .message(fieldError.getDefaultMessage())
                                        .type(Response.Error.Detail.Type.NOT_CORRECT)
                                        .target(fieldError.getField())
                                        .build())
                                .collect(Collectors.toList()))
                        .build()
        );
    }

    @ExceptionHandler(ValidationCollectorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response validationCollectorException(ValidationCollectorException exception) {
        log.debug("{}, cause: {}", exception.toString(), ExceptionUtils.getShortStackTrace(exception));
        return Response.badRequest(
                Response.Error.builder()
                        .message(exception.getLocalizedMessage())
                        .type(Response.Error.Type.VALIDATION_ERROR)
                        .details(exception
                                .getErrors()
                                .stream()
                                .map(errorInfo -> Response.Error.Detail.builder()
                                        .target(errorInfo.getField())
                                        .message(errorInfo.getMessage())
                                        .type(Response.Error.Detail.Type.NOT_CORRECT)
                                        .build())
                                .collect(Collectors.toList()))
                        .build()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response requestBodyException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        log.warn(String.format("Failed to read %s request %s body", request.getMethod(), request.getRequestURI()), exception);
        return Response.badRequest(
                Response.Error.builder()
                        .message("Invalid param")
                        .type(Response.Error.Type.BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Response accessDeniedException(AccessDeniedException exception) {
        log.debug("{}, cause: {}", exception.toString(), ExceptionUtils.getShortStackTrace(exception));
        return Response.forbidden(
                Response.Error.builder()
                        .message(exception.getMessage())
                        .type(Response.Error.Type.FORBIDDEN)
                        .build()
        );
    }

    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Response forbiddenException(ForbiddenException exception) {
        log.debug("{}, cause: {}", exception.toString(), ExceptionUtils.getShortStackTrace(exception));
        return Response.forbidden(
                Response.Error.builder()
                        .message(exception.getMessage())
                        .type(Response.Error.Type.FORBIDDEN)
                        .build()
        );
    }

    @ExceptionHandler({TooManyRequestsException.class})
    @ResponseBody
    public Response tooManyRequestsException(TooManyRequestsException exception) {
        log.debug("{}, cause: {}", exception.toString(), ExceptionUtils.getShortStackTrace(exception));
        return Response.tooManyRequests(
                Response.Error.builder()
                        .type(Response.Error.Type.TOO_MANY_REQUESTS)
                        .build(),
                exception.getRetryAfter().toString()
        );
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response notFoundException(NotFoundException exception) {
        log.debug("{}, cause: {}", exception.toString(), ExceptionUtils.getShortStackTrace(exception));
        return Response.notFound(
                Response.Error.builder()
                        .message(exception.getMessage())
                        .type(Response.Error.Type.NOT_FOUND)
                        .build()
        );
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response notFoundException(EntityNotFoundException exception) {
        log.debug("{}, cause: {}", exception.toString(), ExceptionUtils.getShortStackTrace(exception));
        return Response.notFound(
                Response.Error.builder()
                        .message(exception.getMessage())
                        .type(Response.Error.Type.NOT_FOUND)
                        .build()
        );
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response authException(AuthenticationException exception) {
        log.debug("{}, cause: {}", exception.toString(), ExceptionUtils.getShortStackTrace(exception));
        return Response.unauthorized(
                Response.Error.builder()
                        .message(exception.getMessage())
                        .type(Response.Error.Type.NOT_AUTHORIZED)
                        .build()
        );
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response validationException(ValidationException exception) {
        log.debug("{}, cause: {}", exception.toString(), ExceptionUtils.getShortStackTrace(exception));
        return Response.badRequest(
                Response.Error.builder()
                        .message(exception.getMessage())
                        .type(Response.Error.Type.VALIDATION_ERROR)
                        .detail(Response.Error.Detail.builder()
                                .message(exception.getMessage())
                                .type(Response.Error.Detail.Type.NOT_CORRECT)
                                .target(exception.getField())
                                .build())
                        .build()
        );
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response badRequestException(BadRequestException exception) {
        log.debug("{}, cause: {}", exception.toString(), ExceptionUtils.getShortStackTrace(exception));
        return Response.badRequest(
                Response.Error.builder()
                        .message(exception.getMessage())
                        .type(Response.Error.Type.BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response illegalArgumentException(IllegalArgumentException exception) {
        log.debug("{}, cause: {}", exception.toString(), ExceptionUtils.getShortStackTrace(exception));
        return Response.badRequest(
                Response.Error.builder()
                        .message(exception.getMessage())
                        .type(Response.Error.Type.BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response serverException(Exception exception, HttpServletRequest request) {
        log.error(String.format("Failed to process %s request %s", request.getMethod(), request.getRequestURI()), exception);
        return Response.internalServerError(
                Response.Error.builder()
                        .message("Server failed to process request")
                        .type(Response.Error.Type.REQUEST_FAILED)
                        .build()
        );
    }
}