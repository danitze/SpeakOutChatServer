package com.coursework.speakoutchat.chatservice.controller;

import com.coursework.speakoutchat.chatservice.dto.ChatMessageDto;
import com.coursework.speakoutchat.chatservice.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MessagingController {

    private final SimpMessagingTemplate messagingTemplate;

    private final ChatService service;

    @Autowired
    public MessagingController(SimpMessagingTemplate messagingTemplate, ChatService service) {
        this.messagingTemplate = messagingTemplate;
        this.service = service;
    }

    @MessageMapping("/message")
    public void sendMessageMessageMapping(@Payload ChatMessageDto message) {
        service.saveMessage(message);
        messagingTemplate.convertAndSend("/queue/message/" + message.getReceiverId(), message);
    }

    public void onUserDisconnected(String userId) {
        String partnerId = service.removeAndGetPartner(userId);
        if (partnerId != null) {
            messagingTemplate.convertAndSend("/queue/disconnect/" + partnerId, "");
        }
    }
}
