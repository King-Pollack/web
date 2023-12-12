package com.king.app.application.waiting;

import com.king.app.domain.user.User;

public interface WaitingLogService {
    /**
     * 대기하기 누르기
     * @param userId 정보
     * @param partySize 인원수
     */
    void pressWait(Long userId, Integer partySize);

    /**
     *
     * @param user 정보
     */
    void enter(User user);
}
