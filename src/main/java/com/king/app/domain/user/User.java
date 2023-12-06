package com.king.app.domain.user;

import com.king.app.domain.reserve.ReservationLog;
import com.king.app.domain.type.UserAgeRange;
import com.king.app.domain.type.UserGender;
import com.king.app.domain.waiting.WaitingLog;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "uq_provider_id",
                columnNames = {
                        "providerId",
                }
        )
})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Convert(converter = UserAgeRangeConverter.class)
    private UserAgeRange ageRange; // (String type 1~9: 1세 이상 10세 미만)
    @Enumerated(EnumType.STRING)
    private UserGender gender; // female, male
    private String phoneNumber; // +82 00-0000-0000
    private String nickname;
    private String provider;
    private String providerId;
    private String email;
    @CreatedDate
    private LocalDateTime createdDt;
    @LastModifiedDate
    private LocalDateTime updatedDt;
    private LocalDateTime deletedDt;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ReservationLog> userReservedList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<WaitingLog> userWaitingList;
}
