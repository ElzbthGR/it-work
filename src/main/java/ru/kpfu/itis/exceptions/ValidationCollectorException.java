package ru.kpfu.itis.exceptions;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationCollectorException extends RuntimeException {

    private List<DetailErrorInfo> errors;

    public ValidationCollectorException add(String field, String message) {
        getErrors().add(DetailErrorInfo.builder()
                .field(field)
                .message(message)
                .build());
        return this;
    }

    public boolean isEmpty() {
        return getErrors().isEmpty();
    }

    public void throwIfErrorsNotEmpty() {
        if (!isEmpty()) {
            throw this;
        }
    }

    public List<DetailErrorInfo> getErrors() {
        if (errors == null) {
            errors = Lists.newArrayList();
        }
        return errors;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailErrorInfo {
        private String field;
        private String message;
    }
}
