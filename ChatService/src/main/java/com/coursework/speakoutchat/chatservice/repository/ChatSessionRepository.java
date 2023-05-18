package com.coursework.speakoutchat.chatservice.repository;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ChatSessionRepository {

    private final Map<String, String> sessionToUser = Collections.synchronizedMap(new HashMap<>());

    private final Map<String, String> chatSession = Collections.synchronizedMap(new HashMap<>());

    public void saveChatSession(String sessionId, String senderId, String receiverId) {
        sessionToUser.put(sessionId, senderId);
        chatSession.put(senderId, receiverId);
    }

    @Nullable
    public String removeAndGetPartner(String sessionId) {
        String userId = sessionToUser.remove(sessionId);
        if (userId == null) {
            return null;
        }
        return chatSession.remove(userId);
    }

}
