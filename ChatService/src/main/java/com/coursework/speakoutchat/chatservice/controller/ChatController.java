package com.coursework.speakoutchat.chatservice.controller;

import com.coursework.speakoutchat.chatservice.dto.ChatMessageDto;
import com.coursework.speakoutchat.chatservice.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    private final ChatService service;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate, ChatService service) {
        this.messagingTemplate = messagingTemplate;
        this.service = service;
    }

    @MessageMapping("/message")
    public void sendMessageMessageMapping(@Header("simpSessionId") String sessionId, @Payload ChatMessageDto message) {
        System.out.println(message);
        service.saveChatSession(sessionId, message.getSenderId(), message.getReceiverId());
        service.saveMessage(message);
        messagingTemplate.convertAndSend("/queue/message/" + message.getReceiverId(), message);
    }

    public void onUserDisconnected(String sessionId) {
        String partnerId = service.removeAndGetPartner(sessionId);
        if (partnerId != null) {
            messagingTemplate.convertAndSend("/queue/disconnect/" + partnerId, "");
        }
    }
}
