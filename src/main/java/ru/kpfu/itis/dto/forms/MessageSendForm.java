package ru.kpfu.itis.dto.forms;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.utils.HttpResponseStatus;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendForm {

    @ApiModelProperty(required = true)
    @NotBlank(message = HttpResponseStatus.EMPTY_PARAM)
    private String userMessage;
}
