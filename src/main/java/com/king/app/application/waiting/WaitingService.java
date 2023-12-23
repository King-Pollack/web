package com.king.app.application.waiting;

import com.king.app.domain.waiting.WaitingTeam;

public interface WaitingService {
    WaitingTeam waitingTeam(WaitingTeam waitingTeam);

    void moveOneStepBack(String userId);

    void moveToLast(String userId);

    void waitingCheck(String userId);
}
