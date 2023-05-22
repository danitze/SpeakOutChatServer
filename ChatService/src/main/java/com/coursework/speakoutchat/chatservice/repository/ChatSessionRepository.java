package com.coursework.speakoutchat.chatservice.repository;

import com.coursework.speakoutchat.chatservice.dto.ChatPair;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ChatSessionRepository {

    private final Map<String, String> chatSession = Collections.synchronizedMap(new HashMap<>());

    public void saveChatSession(ChatPair chatPair) {
        chatSession.put(chatPair.getFirstUserId(), chatPair.getSecondUserId());
        chatSession.put(chatPair.getSecondUserId(), chatPair.getFirstUserId());
    }

    @Nullable
    public String removeAndGetPartner(String userId) {
        String partnerId = chatSession.remove(userId);
        if (partnerId == null) {
            return "";
        }
        return partnerId;
    }

}
