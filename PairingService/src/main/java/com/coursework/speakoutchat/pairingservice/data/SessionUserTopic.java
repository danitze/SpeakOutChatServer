package com.coursework.speakoutchat.pairingservice.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionUserTopic {

    private String sessionId;

    private String userId;

    private String topic;

}
