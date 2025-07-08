package org.example.flowin2.domain.chatMessage.repository;

import org.example.flowin2.domain.chatMessage.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
}
