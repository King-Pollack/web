package com.king.app.application.waiting;

public interface WaitingStreamService {
    Long getCurrentWaitingTeam();

    Long getMyWaitingRanking(String userId);
}
