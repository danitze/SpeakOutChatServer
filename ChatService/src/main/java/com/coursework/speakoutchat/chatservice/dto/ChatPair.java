package com.coursework.speakoutchat.chatservice.dto;

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
