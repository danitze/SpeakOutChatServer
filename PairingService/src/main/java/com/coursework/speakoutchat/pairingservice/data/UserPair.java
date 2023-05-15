package com.coursework.speakoutchat.pairingservice.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPair {

    private String firstUserId;

    private String secondUserId;
}
