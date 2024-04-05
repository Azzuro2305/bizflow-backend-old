package org.project.final_backend.repo;

import org.project.final_backend.entity.ChatBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatBoxRepo extends JpaRepository<ChatBox, UUID> {
    ChatBox findBySenderIdAndRecipientId(UUID senderId, UUID recipientId);
    List<ChatBox> findBySenderId(UUID senderId);
}
