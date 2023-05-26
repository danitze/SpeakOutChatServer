package com.coursework.speakoutchat.chatservice.interceptor;

import com.coursework.speakoutchat.chatservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class SessionChannelInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    @Autowired
    public SessionChannelInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            if (accessor.getFirstNativeHeader("Authorization") != null) {
                try {
                    String token = accessor.getFirstNativeHeader("Authorization").substring(7);
                    jwtUtil.validateToken(token);
                } catch (Exception e) {
                    throw new RuntimeException("Unauthorized");
                }
            } else {
                throw new RuntimeException("Unauthorized");
            }
        }
        return message;
    }
}
