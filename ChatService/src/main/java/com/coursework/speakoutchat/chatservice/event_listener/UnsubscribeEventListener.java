package com.coursework.speakoutchat.chatservice.event_listener;

import com.coursework.speakoutchat.chatservice.controller.ChatController;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class UnsubscribeEventListener {

    private final ChatController controller;

    public UnsubscribeEventListener(ChatController controller) {
        this.controller = controller;
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        controller.onUserDisconnected(event.getSessionId());
    }

}
