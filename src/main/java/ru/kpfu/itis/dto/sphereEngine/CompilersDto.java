package ru.kpfu.itis.dto.sphereEngine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilersDto {
    private List<CompilerDto> items;
}
