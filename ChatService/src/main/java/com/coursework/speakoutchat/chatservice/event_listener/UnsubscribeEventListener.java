package com.coursework.speakoutchat.chatservice.event_listener;

import com.coursework.speakoutchat.chatservice.controller.MessagingController;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class UnsubscribeEventListener {

    private final MessagingController controller;

    public UnsubscribeEventListener(MessagingController controller) {
        this.controller = controller;
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = getUserId(accessor);
        controller.onUserDisconnected(userId);
    }

    private String getUserId(StompHeaderAccessor accessor) {
        GenericMessage<?> generic = (GenericMessage<?>) accessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
        if (nonNull(generic)) {
            SimpMessageHeaderAccessor nativeAccessor = SimpMessageHeaderAccessor.wrap(generic);
            List<String> userIdValue = nativeAccessor.getNativeHeader("user-id");

            return isNull(userIdValue) ? null : userIdValue.stream().findFirst().orElse(null);
        }

        return null;
    }
}
