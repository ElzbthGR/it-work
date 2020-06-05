package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResponseDto {
    private boolean rightAnswer;
    private List<ExplanationDto> explanations;
}
