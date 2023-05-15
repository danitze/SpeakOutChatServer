package com.coursework.speakoutchat.pairingservice.repository;

import com.coursework.speakoutchat.pairingservice.data.SessionUserTopic;
import com.coursework.speakoutchat.pairingservice.data.UserPair;
import com.coursework.speakoutchat.pairingservice.data.UserTopic;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class PairingRepository {

    private final List<SessionUserTopic> sessionUserTopics = Collections.synchronizedList(new ArrayList<>());

    @Nullable
    public UserPair pair(String sessionId, UserTopic userTopic) {
        SessionUserTopic sessionUserTopic = sessionUserTopics.stream()
                .filter(sessionUserWithTopic -> sessionUserWithTopic.getTopic().equals(userTopic.getTopic()))
                .findFirst()
                .orElse(null);
        if (sessionUserTopic == null) {
            sessionUserTopics.add(new SessionUserTopic(sessionId, userTopic.getUserId(), userTopic.getTopic()));
            return null;
        }

        sessionUserTopics.remove(sessionUserTopic);
        return new UserPair(userTopic.getUserId(), sessionUserTopic.getUserId());
    }

    public void removeUser(String sessionId) {
        sessionUserTopics.removeIf(sessionUserTopic -> sessionUserTopic.getSessionId().equals(sessionId));
    }

}
