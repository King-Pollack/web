package com.king.app.presentation.api.waiting.response;

import com.king.app.domain.waiting.WaitingTeam;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class WaitingTeamResponse {
    private String phoneNumber;
    private String partySize;

    public static WaitingTeamResponse from(WaitingTeam waitingTeam) {
        return new WaitingTeamResponse(waitingTeam.getPhoneNumber(), waitingTeam.getPartySize());
    }
}