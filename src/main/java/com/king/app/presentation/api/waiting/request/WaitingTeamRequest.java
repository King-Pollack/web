package com.king.app.presentation.api.waiting.request;

import com.king.app.domain.waiting.WaitingTeam;
import lombok.*;

@Getter
public class WaitingTeamRequest {
    private String phoneNumber;
    private String numberOfPeople;

    public WaitingTeam toEntity(Long userId) {
        return new WaitingTeam(userId,this.phoneNumber, this.numberOfPeople);
    }
}