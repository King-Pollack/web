package com.king.app.application.waiting;

import com.king.app.application.user.UserService;
import com.king.app.domain.user.User;
import com.king.app.domain.waiting.WaitingLog;
import com.king.app.infrastructure.repository.waiting.WaitingLogRepository;
import com.king.app.infrastructure.mapper.WaitingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaitingLogServiceImpl implements WaitingLogService{
    private final WaitingLogRepository waitingLogRepository;
    private final WaitingMapper waitingMapper;
    private final UserService userService;

    @Override
    @Transactional
    public void pressWait(Long userId, Integer partySize) {
        User findUser = userService.findById(userId);
        WaitingLog build = WaitingLog.builder()
                .waitingDt(LocalDateTime.now())
                .waitingPartySize(partySize)
                .user(findUser)
                .build();
        waitingLogRepository.save(build);
        log.info("UserId is {} press wait with party size {} at {}",
                findUser.getId(), partySize, build.getWaitingDt());
    }

    @Override
    public void enter(User user) {
        waitingMapper.updateWaitingLog(user.getId());
        log.info("UserId is {} entered at {}", user.getId(), LocalDateTime.now());
    }
}
