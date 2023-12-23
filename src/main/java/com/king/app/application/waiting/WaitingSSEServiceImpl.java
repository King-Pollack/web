package com.king.app.application.waiting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class WaitingSSEServiceImpl implements WaitingSSEService {

    private final ConcurrentHashMap<String, SseEmitter> myRankEmitters = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<SseEmitter> totalTeamEmitters = new CopyOnWriteArrayList<>();
    private final WaitingStreamService waitingStreamService;

    @Override
    public void addTotalTeamEmitter(SseEmitter emitter) {
        Long currentWaitingTeam = waitingStreamService.getCurrentWaitingTeam();
        try {
            totalTeamEmitters.add(emitter);
            emitter.send(currentWaitingTeam);
        } catch (IOException e) {
            emitter.complete();
            totalTeamEmitters.remove(emitter);
        }
    }

    @Override
    public void totalWaitingTeamEventPublish() {
        Long currentWaitingTeam = waitingStreamService.getCurrentWaitingTeam();
        totalTeamEmitters.forEach((SseEmitter emitter) -> {
            try {
                emitter.send(currentWaitingTeam);
            } catch (IOException e) {
                emitter.complete();
                totalTeamEmitters.remove(emitter);
            }
        });
    }

    @Override
    public void detachTotalTeamEmitter(SseEmitter emitter) {
        totalTeamEmitters.remove(emitter);
    }

    @Override
    public void addMyRankEmitter(SseEmitter emitter, String userId) {
        Long currentWaitingTeam = waitingStreamService.getCurrentWaitingTeam();
        Long myRanking = waitingStreamService.getMyWaitingRanking(userId);
        try {
            myRankEmitters.put(userId, emitter);
            emitter.send(currentWaitingTeam+":"+myRanking);
        } catch (IOException e) {
            emitter.complete();
            myRankEmitters.remove(userId);
        }
    }

    @Override
    public void myRankEventPublish() {
        Long currentWaitingTeam = waitingStreamService.getCurrentWaitingTeam();
        myRankEmitters.forEach((String userId, SseEmitter emitter) -> {
            try {
                Long myRanking = waitingStreamService.getMyWaitingRanking(userId);
                emitter.send(currentWaitingTeam+":"+myRanking);
            } catch (IOException e) {
                emitter.complete();
                myRankEmitters.remove(userId);
            }
        });
    }

    @Override
    public void detachMyRankEmitter(String userId) {
        myRankEmitters.remove(userId);
    }


}
