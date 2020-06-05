package ru.kpfu.itis.dto.forms;

import ru.kpfu.itis.utils.AppConstants;
import ru.kpfu.itis.utils.HttpResponseStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
public class AuthorizationForm {

    @Email(message = HttpResponseStatus.INVALID_PARAM)
    @NotBlank(message = HttpResponseStatus.EMPTY_PARAM)
    @ApiModelProperty(required = true, example = AppConstants.EMAIL_EXAMPLE)
    private String login;

    @ApiModelProperty(required = true)
    @NotBlank(message = HttpResponseStatus.EMPTY_PARAM)
    private String password;

    public String getLogin() {
        return login.trim().toLowerCase();
    }

    public String getPassword() {
        return password.trim();
    }
}
