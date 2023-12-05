package com.king.app.domain.reserve;

import com.king.app.domain.type.AccessRoute;
import com.king.app.domain.type.ReservationStatus;
import com.king.app.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime reservationDt; // 예약 누른 시간
    private ReservationStatus status; // "CONFIRMED", "CANCELED", "CHANGED"
    private AccessRoute route; // "COM", "MOBILE"
    private LocalDateTime updatedDt;
    private LocalDateTime deletedDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
