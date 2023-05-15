package com.coursework.speakoutchat.pairingservice.service;

import com.coursework.speakoutchat.pairingservice.data.UserPair;
import com.coursework.speakoutchat.pairingservice.data.UserTopic;
import com.coursework.speakoutchat.pairingservice.repository.PairingRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PairingService {

    private final PairingRepository pairingRepository;

    @Autowired
    public PairingService(PairingRepository pairingRepository) {
        this.pairingRepository = pairingRepository;
    }

    @Nullable
    public UserPair pair(String sessionId, UserTopic userTopic) {
        return pairingRepository.pair(sessionId, userTopic);
    }

    public void removeUser(String sessionId) {
        pairingRepository.removeUser(sessionId);
    }
}
