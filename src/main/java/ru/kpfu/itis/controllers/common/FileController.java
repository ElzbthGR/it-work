package ru.kpfu.itis.controllers.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.dto.FileDto;
import ru.kpfu.itis.mappers.FileDataMapper;
import ru.kpfu.itis.security.CurrentUser;
import ru.kpfu.itis.services.FileDataService;

@Slf4j
@RequiredArgsConstructor
@Api(tags = {"Common.Files"})
@RestController
@RequestMapping(FileController.ROOT_URL)
public class FileController {
    public static final String ROOT_URL = "/v1/files/upload";

    private final FileDataService fileDataService;

    private final FileDataMapper fileDataMapper;

    @ApiOperation("Upload file")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping
    public ResponseEntity<FileDto> upload(@RequestBody final MultipartFile file,
                                          @AuthenticationPrincipal final CurrentUser user) {
        return ResponseEntity.ok(fileDataMapper.apply(fileDataService.upload(file, user.getId())));
    }
}
