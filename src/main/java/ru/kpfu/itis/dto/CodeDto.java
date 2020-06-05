package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.dto.sphereEngine.CompilerDto;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeDto {
    private String template;
    private CompilerDto compiler;
}
