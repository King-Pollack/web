package com.king.app.domain.reserve;

import com.king.app.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReserveLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime waitingDt; // 대기 누른 시간
    private LocalDateTime enteredDt; // 입장한 시간
    private Long waitingPartySize; // 팀 인원 수
    private LocalDateTime updatedDt;
    private LocalDateTime deletedDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
