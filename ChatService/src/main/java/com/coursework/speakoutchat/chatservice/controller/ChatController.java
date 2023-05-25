package com.coursework.speakoutchat.chatservice.controller;

import com.coursework.speakoutchat.chatservice.dto.ChatPair;
import com.coursework.speakoutchat.chatservice.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService service;

    @Autowired
    public ChatController(ChatService service) {
        this.service = service;
    }

    @PostMapping
    public void startChat(@RequestBody ChatPair chatPair) {
        service.saveChatSession(chatPair);
    }

}
