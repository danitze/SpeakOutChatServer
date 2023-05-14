package com.coursework.speakoutchat.pairingservice.event_listener;

import com.coursework.speakoutchat.pairingservice.controller.PairingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class UnsubscribeEventListener {

    private final PairingController pairingController;

    @Autowired
    public UnsubscribeEventListener(PairingController pairingController) {
        this.pairingController = pairingController;
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        System.out.println(event.getSessionId());
        pairingController.removeUser(event.getSessionId());
    }
}
