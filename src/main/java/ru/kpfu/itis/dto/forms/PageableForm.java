package ru.kpfu.itis.dto.forms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import ru.kpfu.itis.utils.HttpResponseStatus;
import ru.kpfu.itis.utils.OffsetBasedPageRequest;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;

@Data
public class PageableForm {

    public static final Integer DEFAULT_OFFSET = 0;
    public static final Integer DEFAULT_LIMIT = 20;

    @Min(value = 0, message = HttpResponseStatus.INVALID_PARAM)
    private Integer offset = DEFAULT_OFFSET;

    @Min(value = 1, message = HttpResponseStatus.INVALID_PARAM)
    private Integer limit = DEFAULT_LIMIT;

    @JsonIgnore
    public Pageable getPageRequest() {
        return OffsetBasedPageRequest.of(getOffset(), getLimit());
    }

    @JsonIgnore
    public Pageable getPageRequest(Sort sort) {
        return OffsetBasedPageRequest.of(getOffset(), getLimit(), sort);
    }
}

