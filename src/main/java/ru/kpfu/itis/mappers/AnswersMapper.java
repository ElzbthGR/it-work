package ru.kpfu.itis.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.AnswerDto;
import ru.kpfu.itis.models.Answer;

import javax.annotation.Nullable;

@Component
@RequiredArgsConstructor
public class AnswersMapper extends DtoMapper<Answer, AnswerDto> {

    @Nullable
    @Override
    public AnswerDto apply(@Nullable Answer answer) {
        if (answer == null) {
            return null;
        }
        AnswerDto dto = new AnswerDto();
        dto.setId(answer.getId());
        dto.setDescription(answer.getDescription());
        return dto;
    }
}
