package ru.kpfu.itis.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TooManyRequestsException extends RuntimeException {
    private Long retryAfter;
}
