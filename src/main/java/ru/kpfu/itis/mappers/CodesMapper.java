package ru.kpfu.itis.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.CodeDto;
import ru.kpfu.itis.dto.sphereEngine.CompilerDto;
import ru.kpfu.itis.models.Code;

import javax.annotation.Nullable;

@Component
@RequiredArgsConstructor
public class CodesMapper extends DtoMapper<Code, CodeDto> {

    @Nullable
    @Override
    public CodeDto apply(@Nullable Code code) {
        if (code == null) {
            return null;
        }
        CodeDto dto = new CodeDto();
        dto.setTemplate(code.getTemplate());
        dto.setCompiler(new CompilerDto(code.getCompilerId(), code.getCompilerName()));
        return dto;
    }
}
