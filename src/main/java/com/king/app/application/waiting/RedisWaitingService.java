package com.king.app.application.waiting;

import com.king.app.application.waiting.dto.WaitingLogEvent;
import com.king.app.domain.waiting.WaitingTeam;
import com.king.app.infrastructure.repository.waiting.WaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RedisWaitingService implements WaitingService {

    private final WaitingRepository waitingRepository;
    private final WaitingSSEServiceImpl sseService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(noRollbackFor = {RuntimeException.class, Exception.class})
    public WaitingTeam waitingTeam(WaitingTeam waitingTeam) {
        WaitingTeam team;
        try {
            team = waitingRepository.save(waitingTeam);
            sseService.totalWaitingTeamEventPublish();
            sseService.myRankEventPublish();
            eventPublisher.publishEvent(new WaitingLogEvent(team.getUserId(), team.getPhoneNumber(), team.getPartySize()));
        } catch (RuntimeException e) {
            //todo: throw custom exception
            waitingRepository.rollback(waitingTeam.toString());
            throw new RuntimeException(e);
        }
        return team;
    }

    @Override
    public void moveOneStepBack(String userId) {
        waitingRepository.moveOneStepBack(userId);
        sseService.totalWaitingTeamEventPublish();
        sseService.myRankEventPublish();
    }

    @Override
    public void moveToLast(String userId) {
        waitingRepository.moveToLast(userId);
        sseService.totalWaitingTeamEventPublish();
        sseService.myRankEventPublish();
    }

    @Override
    public void waitingCheck(String userId) {
        waitingRepository.waitingCheck(userId);
    }
}
