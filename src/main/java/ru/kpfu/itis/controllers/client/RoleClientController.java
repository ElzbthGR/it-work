package ru.kpfu.itis.controllers.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.dto.PageableListDto;
import ru.kpfu.itis.dto.Response;
import ru.kpfu.itis.dto.RoleTypeDto;
import ru.kpfu.itis.dto.forms.PageableForm;
import ru.kpfu.itis.security.annotations.HasRoleClient;
import ru.kpfu.itis.security.annotations.HasRoleClientOrAdmin;
import ru.kpfu.itis.services.RolesService;

import javax.validation.Valid;


@Slf4j
@HasRoleClientOrAdmin
@RequiredArgsConstructor
@Api(tags = {"Common.RoleTypes"})
@RestController
@RequestMapping(RoleClientController.ROOT_URL)
public class RoleClientController {
    public static final String ROOT_URL = "/v1/roles";
    private static final String PAGE_URL = "/page";

    private final RolesService rolesService;

    @ApiOperation("Page role types")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(PAGE_URL)
    public Response<PageableListDto<RoleTypeDto>> page(@Valid @RequestBody final PageableForm form) {
        return Response.ok(rolesService.page(form));
    }
}
