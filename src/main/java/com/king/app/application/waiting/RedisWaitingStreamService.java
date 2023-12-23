package com.king.app.application.waiting;

import com.king.app.infrastructure.repository.waiting.RedisWaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisWaitingStreamService implements WaitingStreamService {

    private final RedisWaitingRepository redisWaitingRepository;

    @Override
    public Long getCurrentWaitingTeam() {
        return redisWaitingRepository.getCurrentWaitingTeam();
    }

    @Override
    public Long getMyWaitingRanking(String userId) {
        return redisWaitingRepository.getMyWaitingRanking(userId) + 1L;
    }

}
