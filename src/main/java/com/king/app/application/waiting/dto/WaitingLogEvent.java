package com.king.app.application.waiting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WaitingLogEvent {
    private Long userId;
    private String phoneNumber;
    private Integer partySize;

    public String getKey() {
        return userId+":"+phoneNumber+":"+partySize;
    }
}