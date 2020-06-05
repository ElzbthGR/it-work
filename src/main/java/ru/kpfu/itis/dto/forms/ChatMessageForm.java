package ru.kpfu.itis.dto.forms;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.utils.HttpResponseStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageForm {
    public static final String RIGHT_FIELD = "right";

    @ApiModelProperty(required = true)
    private String userMessage;

    @ApiModelProperty(required = true)
    @NotBlank(message = HttpResponseStatus.EMPTY_PARAM)
    private String serverMessage;

    @ApiModelProperty(required = true)
    @NotNull(message = HttpResponseStatus.EMPTY_PARAM)
    private Boolean right = Boolean.FALSE;

    @ApiModelProperty(required = true)
    @NotNull(message = HttpResponseStatus.EMPTY_PARAM)
    private Boolean start = Boolean.FALSE;
}
