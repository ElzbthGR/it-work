package ru.kpfu.itis.controllers.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.*;
import ru.kpfu.itis.dto.forms.*;
import ru.kpfu.itis.dto.projection.CompilerProjection;
import ru.kpfu.itis.dto.projection.VerificationTaskResponsesProjection;
import ru.kpfu.itis.dto.sphereEngine.SubmissionForm;
import ru.kpfu.itis.mappers.TasksMapper;
import ru.kpfu.itis.mappers.TasksShortMapper;
import ru.kpfu.itis.mappers.VerificationTaskResponsesMapper;
import ru.kpfu.itis.mappers.VerificationTaskResponsesShortMapper;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.security.CurrentUser;
import ru.kpfu.itis.security.annotations.HasRoleClient;
import ru.kpfu.itis.services.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@HasRoleClient
@RequiredArgsConstructor
@Api(tags = {"Client.Tasks"})
@RestController
@RequestMapping(TaskClientController.ROOT_URL)
public class TaskClientController {
    public static final String ROOT_URL = "/v1/tasks";
    private static final String ONE_URL = "/{taskId}";
    private static final String TEST_RESPONSE_URL = "/test/response";
    private static final String SEQUENCE_RESPONSE_URL = "/sequence/response";
    private static final String RESPONSE_URL = "/common/response";
    private static final String CODE_RESPONSE_URL = "/code/response";
    private static final String ROLE_TYPE_URL = "/{roleType}";
    private static final String PAGE_URL = "/page";
    private static final String PASSED_URL = "/passed";
    private static final String CHECKED_URL = "/checked";
    private static final String CODE_URL = "/{code}";
    private static final String COMPILER_ONE_URL = "/{compilerId}";
    private static final String COMPILER_URL = "/compilers";
    private static final String MESSAGES_URL = "/messages";
    private static final String SEND_URL = "/send";

    private final CodeService codeService;
    private final TasksService tasksService;
    private final AnswersService answersService;
    private final SequencesService sequencesService;
    private final ChatMessagesService chatMessagesService;

    private final TasksMapper tasksMapper;
    private final TasksShortMapper tasksShortMapper;
    private final VerificationTaskResponsesMapper verificationTaskResponsesMapper;
    private final VerificationTaskResponsesShortMapper verificationTaskResponsesShortMapper;

    @ApiOperation("Solve code")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(ONE_URL + CODE_RESPONSE_URL)
    public Response<CodeResultDto> responseCode(@PathVariable final Long taskId,
                                                @Valid @RequestBody final SubmissionForm form,
                                                @AuthenticationPrincipal CurrentUser user) {
        CodeResultDto result = codeService.checkCode(taskId, user.getId(), form);
        return Response.ok(result);
    }

    @ApiOperation("Get task")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @GetMapping(ONE_URL)
    public Response<TaskDto> get(@PathVariable final Long taskId) {
        return Response.ok(tasksMapper.apply(tasksService.get(taskId)));
    }

    @ApiOperation("Send message to chat")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(ONE_URL + MESSAGES_URL + SEND_URL)
    public Response<ChatMessageResponseDto> checkChatMessages(@PathVariable final Long taskId,
                                      @Valid @RequestBody final MessageSendForm form,
                                      @AuthenticationPrincipal final CurrentUser user) {
        ChatMessageResponseDto result = chatMessagesService.checkChatMessages(taskId, form);
        if (result.isRightAnswer() == Boolean.TRUE) {
            tasksService.passTask(user.getId(), taskId);
        }
        return Response.ok(result);
    }

    @ApiOperation("Solve test")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(ONE_URL + TEST_RESPONSE_URL)
    public Response<TestResponseDto> responseTest(@PathVariable final Long taskId,
                                                  @RequestBody final TaskTestClientForm form,
                                                  @AuthenticationPrincipal final CurrentUser user) {
        TestResponseDto result = answersService.checkAnswers(taskId, form.getAnswerIds());
        if (result.isRightAnswer() == Boolean.TRUE) {
            tasksService.passTask(user.getId(), taskId);
        }
        return Response.ok(result);
    }

    @ApiOperation("Solve sequence")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(ONE_URL + SEQUENCE_RESPONSE_URL)
    public Response<SequenceResponseDto> responseSequence(@PathVariable final Long taskId,
                                                          @RequestBody final TaskSequenceClientForm form,
                                                          @AuthenticationPrincipal CurrentUser user) {
        SequenceResponseDto result = sequencesService.checkSequence(taskId, form.getSequenceItemIds());
        if (result.isRightAnswer() == Boolean.TRUE) {
            tasksService.passTask(user.getId(), taskId);
        }
        return Response.ok(result);
    }

    @ApiOperation("Solve common and uml task")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(ONE_URL + RESPONSE_URL)
    public Response responseCommonAndUml(@PathVariable final Long taskId,
                                         @RequestBody final TaskClientForm form,
                                         @AuthenticationPrincipal CurrentUser user) {
        tasksService.sendToCheck(user.getId(), taskId, form.getFileId());
        return Response.accepted();
    }

    @ApiOperation("Page by role type")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(ROLE_TYPE_URL + PAGE_URL)
    public Response<PageableListDto<TaskShortDto>> pageByRoleType(@PathVariable final Task.RoleType roleType,
                                                                  @Valid @RequestBody final PageableForm form) {
        Page<Task> page = tasksService.pageByRoleType(roleType, form);
        return Response.ok(tasksShortMapper.apply(page));
    }

    @ApiOperation("Page passed tasks")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(PASSED_URL + PAGE_URL)
    public Response<PageableListDto<TaskShortDto>> pagePassed(@AuthenticationPrincipal final CurrentUser user,
                                                              @Valid @RequestBody final PageableForm form) {
        Page<Task> page = tasksService.pagePassed(user.getId(), form);
        return Response.ok(tasksShortMapper.apply(page));
    }

    @ApiOperation("Page checked tasks")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(CHECKED_URL + PAGE_URL)
    public Response<PageableListDto<VerificationTaskResponsesShortDto>> pageVerificationTaskResponses(@Valid @RequestBody final PageableForm form,
                                                                                                      @AuthenticationPrincipal final CurrentUser user) {
        Page<VerificationTaskResponsesProjection> page = tasksService.pageResponses(user.getId(), form);
        return Response.ok(verificationTaskResponsesShortMapper.apply(page));
    }

    @ApiOperation("Get verification task response")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(CHECKED_URL + CODE_URL)
    public Response<VerificationTaskResponsesDto> getVerificationTaskResponse(@PathVariable final UUID code) {
        return Response.ok(verificationTaskResponsesMapper.apply(tasksService.getVerificationTaskResponse(code)));
    }

    @ApiOperation("Get template for task")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @GetMapping(ONE_URL + COMPILER_URL + COMPILER_ONE_URL)
    public Response<String> getTemplate(@PathVariable final Long taskId, @PathVariable final Long compilerId) {
        String taskTemplate = tasksService.getTaskTemplate(taskId, compilerId);
        return Response.ok(taskTemplate);
    }

    @ApiOperation("Get available compilers by task")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @GetMapping(ONE_URL + COMPILER_URL)
    public Response<List<CompilerProjection>> getCompilers(@PathVariable final Long taskId) {
        List<CompilerProjection> compilers = tasksService.getCompilers(taskId);
        return Response.ok(compilers);
    }
}
