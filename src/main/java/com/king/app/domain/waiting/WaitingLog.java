package com.king.app.domain.waiting;

import com.king.app.domain.type.Route;
import com.king.app.domain.type.Status;
import com.king.app.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WaitingLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime reservationDt; // 예약 누른 시간
    private Status status; // "CONFIRMED", "CANCELED", "CHANGED"
    private Route route; // "COM", "MOBILE"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
