package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.models.ChatMessage;

import java.util.List;
import java.util.Optional;

public interface ChatMessagesRepository extends JpaRepository<ChatMessage, Long> {

    void deleteAllByTaskId(Long taskId);

    Optional<ChatMessage> findByTaskIdAndUserMessage(Long taskId, String userMessage);

    @Query("select userMessage from ChatMessage where taskId = :taskId")
    List<String> findUserMessageByTaskId(Long taskId);

}
