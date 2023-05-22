package com.coursework.speakoutchat.pairingservice.controller;

import com.coursework.speakoutchat.pairingservice.data.UserPair;
import com.coursework.speakoutchat.pairingservice.data.UserTopic;
import com.coursework.speakoutchat.pairingservice.dto.ChatPair;
import com.coursework.speakoutchat.pairingservice.service.PairingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class PairingController {

    private final SimpMessagingTemplate messagingTemplate;

    private final PairingService pairingService;

    @Autowired
    public PairingController(SimpMessagingTemplate messagingTemplate, PairingService pairingService) {
        this.messagingTemplate = messagingTemplate;
        this.pairingService = pairingService;
    }

    @MessageMapping("/pair")
    public void pairMessageMapping(@Header("simpSessionId") String sessionId, @Payload UserTopic userTopic) {
        UserPair userPair = pairingService.pair(sessionId, userTopic);
        if (userPair == null) {
            return;
        }
        ChatPair chatPair = new ChatPair(userPair.getFirstUserId(), userPair.getSecondUserId());
        new RestTemplate().postForObject("http://localhost:9899/chat", chatPair, Void.class);
        messagingTemplate.convertAndSend("/queue/pair/" + userPair.getFirstUserId(), userPair.getSecondUserId());
        messagingTemplate.convertAndSend("/queue/pair/" + userPair.getSecondUserId(), userPair.getFirstUserId());
    }
}
