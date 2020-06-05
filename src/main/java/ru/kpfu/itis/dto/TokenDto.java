package ru.kpfu.itis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDto {
    private String authToken;
    private Long userId;
    private String role;
}