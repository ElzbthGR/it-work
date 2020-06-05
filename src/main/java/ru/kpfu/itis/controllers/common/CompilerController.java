package ru.kpfu.itis.controllers.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.dto.Response;
import ru.kpfu.itis.dto.sphereEngine.CompilerDto;
import ru.kpfu.itis.services.CodeService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Api(tags = {"Common.Compilers"})
@RestController
@RequestMapping(CompilerController.ROOT_URL)
public class CompilerController {
    public static final String ROOT_URL = "/v1/compilers";
    private static final String TEMPLATE_URK = "/template";
    private static final String ONE_URL = "/{compilerId}";

    private final CodeService codeService;

    @ApiOperation("Get compilers")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @GetMapping
    public Response<List<CompilerDto>> getList() {
        List<CompilerDto> dto = codeService.getCompilers();
        return Response.ok(dto);
    }

    @ApiOperation("Get base template")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @GetMapping(ONE_URL + TEMPLATE_URK)
    public Response<String> getBaseTemplate(@PathVariable final Long compilerId) {
        String baseTemplate = codeService.getBaseTemplate(compilerId);
        return Response.ok(baseTemplate);
    }
}