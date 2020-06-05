package ru.kpfu.itis.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.forms.PageableForm;
import ru.kpfu.itis.dto.forms.TaskForm;
import ru.kpfu.itis.dto.forms.VerificationTaskResponseForm;
import ru.kpfu.itis.dto.projection.CompilerProjection;
import ru.kpfu.itis.dto.projection.VerificationTaskProjection;
import ru.kpfu.itis.dto.projection.VerificationTaskResponsesProjection;
import ru.kpfu.itis.exceptions.NotFoundException;
import ru.kpfu.itis.exceptions.ValidationException;
import ru.kpfu.itis.models.Code;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.models.TaskResponse;
import ru.kpfu.itis.models.UserTask;
import ru.kpfu.itis.repositories.TaskResponsesRepository;
import ru.kpfu.itis.repositories.TasksRepository;
import ru.kpfu.itis.repositories.UserTasksRepository;
import ru.kpfu.itis.repositories.VerificationTaskRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TasksService {

    private final TasksRepository tasksRepository;
    private final VerificationTaskRepository verificationTaskRepository;
    private final TaskResponsesRepository taskResponsesRepository;
    private final UserTasksRepository userTasksRepository;

    private final FileDataService fileDataService;
    private final AnswersService answersService;
    private final SequencesService sequencesService;
    private final CodeService codeService;
    private final ChatMessagesService chatMessagesService;

    @Transactional
    public Task create(final TaskForm form) {
        log.info("Creating task");
        if (form == null) {
            return null;
        }
        Task task = new Task();
        fill(task, form);
        return tasksRepository.save(task);
    }

    @Transactional
    public Task update(final Long taskId, final TaskForm form) {
        log.info("Updating task with id {}", taskId);
        if (form == null) {
            return null;
        }
        Task task = get(taskId);
        fill(task, form);
        return tasksRepository.save(task);
    }

    private void fill(final Task task, final TaskForm form) {
        log.info("Filling task info");
        answersService.removeAll(task.getId());
        sequencesService.removeAll(task.getId());
        codeService.removeAll(task.getId());
        chatMessagesService.removeAll(task.getId());
        task.setDescription(form.getDescription())
                .setTitle(form.getTitle())
                .setRoleType(form.getRoleType())
                .setType(form.getType());
        task.setFiles(fileDataService.getAllByIds(form.getFileIds()));
        fileDataService.markAllAsUsed(form.getFileIds());
    }

    @Transactional
    public void delete(final Long taskId) {
        log.info("Deleting task with id {}", taskId);
//        tasksRepository.markAsDeleted(taskId);
        tasksRepository.delete(get(taskId));
    }

    public void passTask(final Long userId, final Long taskId) {
        log.info("Passing task with id {} for user with id {}", taskId, userId);
        UserTask.Id id = UserTask.Id.builder()
                .taskId(taskId)
                .userId(userId)
                .build();
        UserTask userTask = UserTask.builder()
                .id(id)
                .build();
        userTasksRepository.save(userTask);
    }

    public void sendToCheck(final Long userId, final Long taskId, final Long fileId) {
        log.info("Sending task with id {} from user with id {} to admin for verification", taskId, userId);
        if (taskId == null || userId == null || fileId == null) {
            return;
        }
        Task.VerificationTask.Id id = Task.VerificationTask.Id.builder()
                .fileId(fileId)
                .userId(userId)
                .taskId(taskId)
                .build();
        Task.VerificationTask verificationTask = Task.VerificationTask.builder()
                .id(id)
                .build();
        verificationTaskRepository.save(verificationTask);
    }

    public Page<VerificationTaskProjection> pageVerificationTasks(final PageableForm form) {
        log.info("Page verification tasks");
        return verificationTaskRepository.page(form);
    }

    public Page<Task> pageByRoleType(final Task.RoleType roleType, final PageableForm form) {
        log.info("Page tasks by role type");
        return tasksRepository.page(roleType, form);
    }

    public Page<Task> pagePassed(final Long userId, final PageableForm form) {
        log.info("Page passed tasks by user with id {}", userId);
        return tasksRepository.page(userId, form);
    }

    public Page<VerificationTaskResponsesProjection> pageResponses(final Long userId, final PageableForm form) {
        log.info("Page passed tasks by user with id {}", userId);
        return verificationTaskRepository.pageResponses(userId, form);
    }

    public Task get(final Long taskId) {
        log.info("Getting task with id {}", taskId);
        return tasksRepository.findByIdAndDeletedFalse(taskId)
                .orElseThrow(() -> new NotFoundException(String.format("Task by id %s not found", taskId)));
    }

    public VerificationTaskProjection getVerificationTask(final UUID code) {
        log.info("Getting verification task projection with code {}", code);
        return verificationTaskRepository.get(code);
    }

    public VerificationTaskResponsesProjection getVerificationTaskResponse(final UUID code) {
        log.info("Getting verification task response projection with code {}", code);
        return verificationTaskRepository.getResponse(code);
    }

    public String getTaskTemplate(final Long taskId, final Long compilerId) {
        log.info("Getting task with id {} with type CODE template by compiler id {}", taskId, compilerId);
        Task task = get(taskId);
        if (!task.getType().equals(Task.Type.CODE)) {
            throw new ValidationException("Type does not match", TaskForm.TYPE_FIELD);
        }
        List<Long> taskCompilerIds = task.getCodes().stream()
                .map(Code::getCompilerId)
                .collect(Collectors.toList());
        if (!taskCompilerIds.contains(compilerId)) {
            throw new NotFoundException(
                    String.format("Task code with id %s not found for compiler with id %s", taskId, compilerId)
            );
        }
        return tasksRepository.findTemplateByTaskIdAndCompilerId(taskId, compilerId);
    }

    public List<CompilerProjection> getCompilers(final Long taskId) {
        return tasksRepository.findCodeCompilerIdAndCompilerNameByTaskId(taskId);
    }

    @Transactional
    public void sendResponse(final UUID code, final VerificationTaskResponseForm form) {
        log.info("Sending response by code {}", code);
        if (form == null) {
            return;
        }
        Task.VerificationTask verificationTask = get(code);
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setText(form.getText())
                .setPassed(form.getPassed())
                .setFiles(fileDataService.getAllByIds(form.getFileIds()));
        taskResponsesRepository.save(taskResponse);
        verificationTask.setTaskResponse(taskResponse);
        verificationTask.setTaskResponseId(taskResponse.getId());
        verificationTaskRepository.save(verificationTask);
        if (form.getPassed()) {
            passTask(verificationTask.getId().getUserId(), verificationTask.getId().getTaskId());
        }
    }

    public Task.VerificationTask get(final UUID code) {
        log.info("Getting verification task with code {}", code);
        return verificationTaskRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(String.format("Verification task by code %s not found", code)));
    }

    public Task.Type getType(final Long taskId) {
        log.info("Getting task type with id {}", taskId);
        return tasksRepository.findTypeByTaskIdAndDeletedFalse(taskId);
    }
}
