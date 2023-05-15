package com.coursework.speakoutchat.pairingservice.event_listener;

import com.coursework.speakoutchat.pairingservice.service.PairingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class UnsubscribeEventListener {

    private final PairingService pairingService;

    @Autowired
    public UnsubscribeEventListener(PairingService pairingService) {
        this.pairingService = pairingService;
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        pairingService.removeUser(event.getSessionId());
    }
}
