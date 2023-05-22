package com.coursework.speakoutchat.chatservice.service;

import com.coursework.speakoutchat.chatservice.dto.ChatMessageDto;
import com.coursework.speakoutchat.chatservice.dto.ChatPair;
import com.coursework.speakoutchat.chatservice.model.ChatMessage;
import com.coursework.speakoutchat.chatservice.repository.ChatMessageRepository;
import com.coursework.speakoutchat.chatservice.repository.ChatSessionRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class ChatService {

    private final ChatMessageRepository messageRepository;

    private final ChatSessionRepository sessionRepository;

    @Autowired
    public ChatService(ChatMessageRepository messageRepository, ChatSessionRepository sessionRepository) {
        this.messageRepository = messageRepository;
        this.sessionRepository = sessionRepository;
    }

    public void saveMessage(ChatMessageDto dao) {
        ChatMessage message = ChatMessage.builder()
                .senderId(dao.getSenderId())
                .receiverId(dao.getReceiverId())
                .content(dao.getContent())
                .date(new Date(dao.getTimeStamp()))
                .build();
        messageRepository.save(message);
    }

    public void saveChatSession(ChatPair chatPair) {
        sessionRepository.saveChatSession(chatPair);
    }

    @Nullable
    public String removeAndGetPartner(String userId) {
        return sessionRepository.removeAndGetPartner(userId);
    }
}
