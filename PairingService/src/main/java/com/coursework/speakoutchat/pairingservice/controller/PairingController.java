package com.coursework.speakoutchat.pairingservice.controller;

import com.coursework.speakoutchat.pairingservice.data.SessionUserTopic;
import com.coursework.speakoutchat.pairingservice.data.UserTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class PairingController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public PairingController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    private final List<SessionUserTopic> sessionUserTopics = Collections.synchronizedList(new ArrayList<>());

    @MessageMapping("/pair")
    public void echoMessageMapping(@Header("simpSessionId") String sessionId, @Payload UserTopic userTopic) {
        SessionUserTopic sessionUserTopic = sessionUserTopics.stream()
                .filter(sessionUserWithTopic -> sessionUserWithTopic.getTopic().equals(userTopic.getTopic()))
                .findFirst()
                .orElse(null);
        if (sessionUserTopic == null) {
            sessionUserTopics.add(new SessionUserTopic(sessionId, userTopic.getUserId(), userTopic.getTopic()));
            return;
        }
        sessionUserTopics.remove(sessionUserTopic);
        messagingTemplate.convertAndSend("/queue/greetings/" + userTopic.getUserId(), sessionUserTopic.getUserId());
        messagingTemplate.convertAndSend("/queue/greetings/" + sessionUserTopic.getUserId(), userTopic.getUserId());
    }

    public void removeUser(String sessionId) {
        sessionUserTopics.removeIf(sessionUserTopic -> sessionUserTopic.getSessionId().equals(sessionId));
    }
}
