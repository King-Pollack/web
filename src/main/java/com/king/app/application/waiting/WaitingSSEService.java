package com.king.app.application.waiting;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface WaitingSSEService {
    void addTotalTeamEmitter(SseEmitter emitter);

    void totalWaitingTeamEventPublish();

    void detachTotalTeamEmitter(SseEmitter emitter);

    void addMyRankEmitter(SseEmitter emitter, String userId);

    void myRankEventPublish();

    void detachMyRankEmitter(String userId);
}
