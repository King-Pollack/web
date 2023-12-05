package com.king.app.domain.user;

import com.king.app.domain.reserve.ReserveLog;
import com.king.app.domain.waiting.WaitingLog;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String ageRange; // (String type 1~9: 1세 이상 10세 미만)
    private String gender; // female, male
    private String phoneNumber; // +82 00-0000-0000
    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;
    private LocalDateTime deletedDt;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ReserveLog> userReservedList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<WaitingLog> userWaitingList;
}
