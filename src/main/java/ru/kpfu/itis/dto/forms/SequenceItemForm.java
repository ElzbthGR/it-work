package ru.kpfu.itis.dto.forms;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.utils.HttpResponseStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SequenceItemForm {
    public static final String ITEM_ORDER_FILED = "itemOrder";

    @ApiModelProperty(required = true)
    @NotBlank(message = HttpResponseStatus.EMPTY_PARAM)
    private String description;

    @ApiModelProperty(required = true)
    @NotNull(message = HttpResponseStatus.EMPTY_PARAM)
    @Min(1L)
    private Long itemOrder;
}
