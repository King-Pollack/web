package com.king.app.infrastructure.repository.waiting;

import com.king.app.domain.waiting.WaitingTeam;

public interface WaitingRepository {
    WaitingTeam save(WaitingTeam waitingTeam);

    void moveOneStepBack(String userId);

    void moveToLast(String userId);

    void rollback(String key);
}
