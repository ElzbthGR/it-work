package ru.kpfu.itis.dto.forms;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.utils.HttpResponseStatus;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskClientForm {

    @ApiModelProperty(required = true)
    @NotNull(message = HttpResponseStatus.EMPTY_PARAM)
    private Long fileId;
}
