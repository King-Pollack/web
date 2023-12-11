package com.king.app.application.waiting;

import com.king.app.domain.user.User;

public interface WaitingLogService {
    /**
     * 대기하기 누르기
     * @param user 정보
     * @param partySize 인원수
     */
    void pressWait(User user, Integer partySize);

    /**
     *
     * @param user 정보
     */
    void enter(User user);
}
