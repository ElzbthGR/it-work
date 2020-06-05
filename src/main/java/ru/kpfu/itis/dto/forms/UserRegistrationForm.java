package ru.kpfu.itis.dto.forms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.kpfu.itis.services.validation.annotations.UniqueUserEmail;
import ru.kpfu.itis.utils.AppConstants;
import ru.kpfu.itis.utils.HttpResponseStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRegistrationForm {

    @ApiModelProperty(required = true)
    @NotBlank(message = HttpResponseStatus.EMPTY_PARAM)
    private String firstName;

    @ApiModelProperty(required = true)
    @NotBlank(message = HttpResponseStatus.EMPTY_PARAM)
    private String lastName;

    @UniqueUserEmail
    @ApiModelProperty(required = true, example = AppConstants.EMAIL_EXAMPLE)
    @Email(message = HttpResponseStatus.INVALID_PARAM)
    @NotBlank(message = HttpResponseStatus.EMPTY_PARAM)
    private String email;

    @ApiModelProperty(required = true)
    @NotBlank(message = HttpResponseStatus.EMPTY_PARAM)
    private String password;
}
