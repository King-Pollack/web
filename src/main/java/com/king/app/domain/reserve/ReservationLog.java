package com.king.app.domain.reserve;

import com.king.app.domain.type.AccessRoute;
import com.king.app.domain.type.ReservationStatus;
import com.king.app.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ReservationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status; // "CONFIRMED", "CANCELED", "CHANGED"
    @Enumerated(EnumType.STRING)
    private AccessRoute route; // "COM", "MOBILE"
    @CreatedDate
    private LocalDateTime reservationDt; // 예약 누른 시간
    @LastModifiedDate
    private LocalDateTime updatedDt;
    private LocalDateTime deletedDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
