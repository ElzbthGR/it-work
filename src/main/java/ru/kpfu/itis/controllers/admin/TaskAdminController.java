package ru.kpfu.itis.controllers.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.*;
import ru.kpfu.itis.dto.forms.*;
import ru.kpfu.itis.dto.projection.VerificationTaskProjection;
import ru.kpfu.itis.dto.sphereEngine.SubmissionForm;
import ru.kpfu.itis.exceptions.ValidationException;
import ru.kpfu.itis.mappers.*;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.security.CurrentUser;
import ru.kpfu.itis.security.annotations.HasRoleAdmin;
import ru.kpfu.itis.services.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@HasRoleAdmin
@RequiredArgsConstructor
@Api(tags = {"Admin.Tasks"})
@RestController
@RequestMapping(TaskAdminController.ROOT_URL)
public class TaskAdminController {
    public static final String ROOT_URL = "/v1/admin/tasks";
    private static final String ONE_URL = "/{taskId}";
    private static final String ADDING_ANSWERS_URL = "/addAnswers";
    private static final String ADDING_SEQUENCE_URL = "/addSequence";
    private static final String ADDING_CODE_URL = "/addCode";
    private static final String ADDING_CHAT_MESSAGES_URL = "/addChatMessages";
    private static final String PAGE_URL = "/page";
    private static final String ROLE_TYPE_URL = "/{roleType}";
    private static final String CODE_URL = "/{code}";
    private static final String SEND_URL = "/send";
    private static final String CHECK_URL = "/check";
    private static final String RUN_CODE_URL = "/code/run";

    private final TasksService tasksService;
    private final AnswersService answersService;
    private final SequencesService sequencesService;
    private final CodeService codeService;
    private final ChatMessagesService chatMessagesService;

    private final TasksMapper tasksMapper;
    private final TasksShortMapper tasksShortMapper;
    private final VerificationTasksMapper verificationTasksMapper;
    private final VerificationTasksShortMapper verificationTasksShortMapper;

    @ApiOperation("Create task")
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
    public Response<TaskDto> create(@RequestBody @Valid final TaskForm form) {
        return Response.created(tasksMapper.apply(tasksService.create(form)));
    }

    @ApiOperation("Update task")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PutMapping(ONE_URL)
    public Response<TaskDto> update(@PathVariable final Long taskId,
                                    @RequestBody @Valid final TaskForm form) {
        return Response.ok(tasksMapper.apply(tasksService.update(taskId, form)));
    }

    @ApiOperation("Delete task")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @DeleteMapping(ONE_URL)
    public Response delete(@PathVariable final Long taskId) {
        tasksService.delete(taskId);
        return Response.noContent();
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

    @ApiOperation("Add answers to task")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(ONE_URL + ADDING_ANSWERS_URL)
    public Response<TaskDto> addAnswers(@PathVariable final Long taskId,
                                        @Valid @RequestBody final List<AnswerForm> forms) {
        Task task = tasksService.get(taskId);
        return Response.ok(tasksMapper.apply(answersService.addAnswers(task, forms)));
    }

    @ApiOperation("Add sequence to task")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(ONE_URL + ADDING_SEQUENCE_URL)
    public Response<TaskDto> addSequence(@PathVariable final Long taskId,
                                         @Valid @RequestBody final List<SequenceItemForm> forms) {
        Task task = tasksService.get(taskId);
        return Response.ok(tasksMapper.apply(sequencesService.addSequenceItems(task, forms)));
    }

    @ApiOperation("Add code to task")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(ONE_URL + ADDING_CODE_URL)
    public Response<TaskDto> addCode(@PathVariable final Long taskId,
                                     @Valid @RequestBody final List<CodeForm> forms) {
        Task task = tasksService.get(taskId);
        return Response.ok(tasksMapper.apply(codeService.addCodes(task, forms)));
    }

    @ApiOperation("Add chat messages to task")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(ONE_URL + ADDING_CHAT_MESSAGES_URL)
    public Response<TaskDto> addChatMessages(@PathVariable final Long taskId,
                                             @Valid @RequestBody final List<ChatMessageForm> forms) {
        Task task = tasksService.get(taskId);
        return Response.ok(tasksMapper.apply(chatMessagesService.addChatMessage(task, forms)));
    }

    @ApiOperation("Page verification tasks")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(CHECK_URL + PAGE_URL)
    public Response<PageableListDto<VerificationTaskShortDto>> pageVerificationTasks(@Valid @RequestBody final PageableForm form) {
        Page<VerificationTaskProjection> pageVerificationTasks = tasksService.pageVerificationTasks(form);
        return Response.ok(verificationTasksShortMapper.apply(pageVerificationTasks));
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

    @ApiOperation("Get verification task")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @GetMapping(CHECK_URL + CODE_URL)
    public Response<VerificationTaskDto> getVerificationTask(@PathVariable final UUID code) {
        VerificationTaskProjection projection = tasksService.getVerificationTask(code);
        return Response.ok(verificationTasksMapper.apply(projection));
    }

    @ApiOperation("Send response to verification task")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(CHECK_URL + CODE_URL + SEND_URL)
    public Response sendResponse(@PathVariable final UUID code,
                                 @Valid @RequestBody VerificationTaskResponseForm form) {
        tasksService.sendResponse(code, form);
        return Response.ok();
    }

    @ApiOperation("Run code")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header",
                    defaultValue = "Bearer %JWTTOKEN%",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping(ONE_URL + RUN_CODE_URL)
    public Response<CodeResultDto> runCode(@PathVariable final Long taskId,
                                           @Valid @RequestBody final SubmissionForm form) {
        Task.Type type = tasksService.getType(taskId);
        if (!type.equals(Task.Type.CODE)) {
            throw new ValidationException("Type does not match", TaskForm.TYPE_FIELD);
        }
        CodeResultDto dto = codeService.getResult(form);
        return Response.ok(dto);
    }
}
