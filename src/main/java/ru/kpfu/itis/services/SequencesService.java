package ru.kpfu.itis.services;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.SequenceResponseDto;
import ru.kpfu.itis.dto.forms.SequenceItemForm;
import ru.kpfu.itis.dto.forms.TaskForm;
import ru.kpfu.itis.exceptions.ValidationException;
import ru.kpfu.itis.models.SequenceItem;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.repositories.SequencesRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SequencesService {

    private final SequencesRepository sequencesRepository;

    public Task addSequenceItems(final Task task, final List<SequenceItemForm> forms) {
        log.info("Adding sequence items to task with id {}", task.getId());
        if (!task.getType().equals(Task.Type.SEQUENCE)) {
            throw new ValidationException("Type does not match", TaskForm.TYPE_FIELD);
        }
        if (forms == null) {
            return null;
        }
        List<SequenceItem> sequenceItems = task.getSequenceItems();
        if (!CollectionUtils.isEmpty(sequenceItems)) {
            sequenceItems.clear();
        }
        validateSequenceItemsOrder(forms);
        List<SequenceItem> newSequenceItems = Lists.newArrayList();
        forms.forEach(form -> newSequenceItems.add(
                SequenceItem.builder()
                        .description(form.getDescription())
                        .itemOrder(form.getItemOrder())
                        .taskId(task.getId())
                        .build()
                )
        );
        sequencesRepository.saveAll(newSequenceItems);
        task.setSequenceItems(newSequenceItems);
        return task;
    }

    public SequenceResponseDto checkSequence(final Long taskId, final List<Long> sequenceItemIds) {
        log.info("Checking sequence items with ids {} for task with id {}", sequenceItemIds, taskId);
        if (CollectionUtils.isEmpty(sequenceItemIds)) {
            throw new ValidationException("There are no sequence items", SequenceItem.ID_COLUMN_NAME);
        }
        sequenceItemIds.forEach(sequenceItemId -> {
            if (sequenceItemId <= 0) {
                throw new ValidationException("Ids cannot be <= 0", SequenceItem.ID_COLUMN_NAME);
            }
        });
        List<Long> correctSequence = sequencesRepository.findIdsByTaskIdOrderByItemOrderAsc(taskId);
        boolean rightAnswer = correctSequence.equals(sequenceItemIds);
        SequenceResponseDto dto = new SequenceResponseDto();
        dto.setRightAnswer(rightAnswer);
        return dto;
    }

    private void validateSequenceItemsOrder(final List<SequenceItemForm> forms) {
        int sequenceSize = forms.size();
        Set<Long> uniqueOrders = forms
                .stream()
                .map(SequenceItemForm::getItemOrder)
                .collect(Collectors.toSet());
        if (uniqueOrders.size() != sequenceSize) {
            throw new ValidationException("Order is not serial", SequenceItemForm.ITEM_ORDER_FILED);
        }
        for (Long order : uniqueOrders) {
            if (order <= 0 || order > sequenceSize) {
                throw new ValidationException("Order is not serial", SequenceItemForm.ITEM_ORDER_FILED);
            }
        }
    }

    @Transactional
    public void removeAll(final Long taskId) {
        log.info("Removing all sequence items by task with id {}", taskId);
        sequencesRepository.deleteAllByTaskId(taskId);
    }
}
