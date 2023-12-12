package com.king.app.domain.waiting;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class WaitingTeam {
    private Long userId;
    private String phoneNumber;
    private Integer partySize;

    @Override
    public String toString() {
        return userId+":"+phoneNumber+":"+ partySize;
    }
}