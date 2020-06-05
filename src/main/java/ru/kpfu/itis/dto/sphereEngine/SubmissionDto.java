package ru.kpfu.itis.dto.sphereEngine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubmissionDto {
    private Long id;
    private boolean executing;
    private CompilerDto compiler;
    private ResultDto result;
}
