package com.coursework.speakoutchat.chatservice.event_listener;

import com.coursework.speakoutchat.chatservice.controller.MessagingController;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class ChatEventListener {

    private final MessagingController controller;

    private final Map<String, String> sessions;

    public ChatEventListener(MessagingController controller) {
        this.controller = controller;
        sessions = Collections.synchronizedMap(new HashMap<>());
    }

    @EventListener
    public void handleSessionConnect(SessionConnectedEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = event.getMessage().getHeaders().get("simpSessionId", String.class);
        sessions.put(sessionId, getUserId(accessor));
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        String userId = sessions.getOrDefault(event.getSessionId(), null);
        controller.onUserDisconnected(userId);
    }

    private String getUserId(StompHeaderAccessor accessor) {
        GenericMessage<?> generic = (GenericMessage<?>) accessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
        if (nonNull(generic)) {
            SimpMessageHeaderAccessor nativeAccessor = SimpMessageHeaderAccessor.wrap(generic);
            List<String> userIdValue = nativeAccessor.getNativeHeader("user_id");

            return isNull(userIdValue) ? null : userIdValue.stream().findFirst().orElse(null);
        }

        return null;
    }
}
