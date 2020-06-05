package ru.kpfu.itis.dto.forms;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.utils.HttpResponseStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskForm {
    public static final String TYPE_FIELD = "type";

    @ApiModelProperty(required = true)
    @NotBlank(message = HttpResponseStatus.EMPTY_PARAM)
    private String description;

    @ApiModelProperty(required = true)
    @NotBlank(message = HttpResponseStatus.EMPTY_PARAM)
    private String title;

    @ApiModelProperty(required = true)
    @NotNull(message = HttpResponseStatus.EMPTY_PARAM)
    private Task.RoleType roleType;

    @ApiModelProperty(required = true)
    @NotNull(message = HttpResponseStatus.EMPTY_PARAM)
    private Task.Type type = Task.Type.COMMON;
    private List<Long> fileIds;
}
