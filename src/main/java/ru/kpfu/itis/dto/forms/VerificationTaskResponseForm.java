package ru.kpfu.itis.dto.forms;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.utils.HttpResponseStatus;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationTaskResponseForm {

    @ApiModelProperty(required = true)
    @NotBlank(message = HttpResponseStatus.EMPTY_PARAM)
    private String text;

    @ApiModelProperty(required = true)
    private Boolean passed = Boolean.FALSE;

    @ApiModelProperty(required = true)
    private List<Long> fileIds;
}
