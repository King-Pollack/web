package com.king.app.application.waiting;

import com.king.app.application.waiting.dto.WaitingLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class WaitingLogEventListener {

    private final WaitingLogService waitingLogService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void transactionalEventListenerAfterCommit(WaitingLogEvent event) {
        waitingLogService.pressWait(event.getUserId(), event.getPartySize());
        log.info("commit and event key {}", event.getKey());
    }

}