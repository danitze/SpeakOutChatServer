package com.coursework.speakoutchat.chatservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMessageDto {

    private String senderId;

    private String receiverId;

    private String content;

    private Long timeStamp;

}
