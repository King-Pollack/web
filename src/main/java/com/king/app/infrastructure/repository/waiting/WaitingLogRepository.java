package com.king.app.infrastructure.repository.waiting;

import com.king.app.domain.waiting.WaitingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitingLogRepository extends JpaRepository<WaitingLog, Long> {
}
