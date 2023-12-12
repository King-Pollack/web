package com.king.app.infrastructure.repository.waiting;

import com.king.app.domain.waiting.WaitingTeam;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class RedisWaitingRepository implements WaitingRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String WAITING_KEY = "waiting";

    public RedisWaitingRepository(@Qualifier("redisTemplateDB") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public WaitingTeam save(WaitingTeam waitingTeam) {
        String phoneAndNumberOfPeopleCount = redisTemplate.opsForValue().get(String.valueOf(waitingTeam.getUserId()));
        if (StringUtils.isNotBlank(phoneAndNumberOfPeopleCount)) {
            throw new IllegalArgumentException("Already waiting userId: " + waitingTeam.getUserId());
        }
        redisTemplate.opsForZSet().add(WAITING_KEY, waitingTeam.toString(), System.currentTimeMillis());
        redisTemplate.opsForValue().set(String.valueOf(waitingTeam.getUserId()), waitingTeam.getPhoneNumber()+":"+waitingTeam.getPartySize());
        return waitingTeam;
    }

    @Override
    public void moveOneStepBack(String userId) {
        String redisKey = makeRedisKey(userId);
        Double score = redisTemplate.opsForZSet().score(WAITING_KEY, redisKey);
        Set<ZSetOperations.TypedTuple<String>> range = redisTemplate.opsForZSet().rangeByScoreWithScores(WAITING_KEY, score + 1, Double.MAX_VALUE, 0, 1);
        if (range != null && !range.isEmpty()) {
            Double nextScore = range.iterator().next().getScore();
            redisTemplate.opsForZSet().add(WAITING_KEY, redisKey, nextScore + 1);
        }
    }

    @Override
    public void moveToLast(String userId) {
        String redisKey = makeRedisKey(userId);
        Set<String> range = redisTemplate.opsForZSet().reverseRange(WAITING_KEY, 0,0);
        if (range != null && !range.isEmpty()) {
            Double maxScore = redisTemplate.opsForZSet().score(WAITING_KEY, range.iterator().next());
            redisTemplate.opsForZSet().add(WAITING_KEY, redisKey, maxScore + 1);
        }
    }

    private String makeRedisKey(String userId) {
        String phoneAndNumberOfPeopleCount = redisTemplate.opsForValue().get(userId);
        if (StringUtils.isBlank(phoneAndNumberOfPeopleCount)) {
            throw new IllegalArgumentException("Users who did not wait userId: " + userId);
        }
        return userId + ":" + phoneAndNumberOfPeopleCount;
    }


}
