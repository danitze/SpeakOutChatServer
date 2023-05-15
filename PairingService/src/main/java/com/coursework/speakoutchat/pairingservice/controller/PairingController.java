package com.coursework.speakoutchat.pairingservice.controller;

import com.coursework.speakoutchat.pairingservice.data.SessionUserTopic;
import com.coursework.speakoutchat.pairingservice.data.UserPair;
import com.coursework.speakoutchat.pairingservice.data.UserTopic;
import com.coursework.speakoutchat.pairingservice.service.PairingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class PairingController {

    private final SimpMessagingTemplate messagingTemplate;

    private final PairingService pairingService;

    @Autowired
    public PairingController(SimpMessagingTemplate messagingTemplate, PairingService pairingService) {
        this.messagingTemplate = messagingTemplate;
        this.pairingService = pairingService;
    }

    private final List<SessionUserTopic> sessionUserTopics = Collections.synchronizedList(new ArrayList<>());

    @MessageMapping("/pair")
    public void pairMessageMapping(@Header("simpSessionId") String sessionId, @Payload UserTopic userTopic) {
        UserPair userPair = pairingService.pair(sessionId, userTopic);
        if (userPair == null) {
            return;
        }
        messagingTemplate.convertAndSend("/queue/greetings/" + userPair.getFirstUserId(), userPair.getSecondUserId());
        messagingTemplate.convertAndSend("/queue/greetings/" + userPair.getSecondUserId(), userPair.getFirstUserId());
    }

    public void removeUser(String sessionId) {
        sessionUserTopics.removeIf(sessionUserTopic -> sessionUserTopic.getSessionId().equals(sessionId));
    }
}
