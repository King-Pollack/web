package com.king.app.application.waiting;

import com.king.app.domain.waiting.WaitingTeam;
import com.king.app.infrastructure.repository.waiting.WaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisWaitingService implements WaitingService{

    private final WaitingRepository waitingRepository;

    @Override
    public WaitingTeam waitingTeam(WaitingTeam waitingTeam) {
        return waitingRepository.save(waitingTeam);
    }

    @Override
    public void moveOneStepBack(String userId) {
        waitingRepository.moveOneStepBack(userId);
    }

    @Override
    public void moveToLast(String userId) {
        waitingRepository.moveToLast(userId);
    }
}
