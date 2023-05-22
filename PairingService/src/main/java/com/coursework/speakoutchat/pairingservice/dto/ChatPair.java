package com.coursework.speakoutchat.pairingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatPair {
    private String firstUserId;
    private String secondUserId;
}
