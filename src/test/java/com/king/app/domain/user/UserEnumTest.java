package com.king.app.domain.user;

import com.king.app.domain.type.UserAgeRange;
import com.king.app.domain.type.UserGender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserEnumTest {
    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    void user_enum_test() {
    public void user_enum_test() {
        User user = User.builder()
                .ageRange(UserAgeRange.AGE_1_9)
                .gender(UserGender.male)
                .phoneNumber("+82 00-0000-0000")
                .nickname("nickname01")
                .createdDt(LocalDateTime.now())
                .updatedDt(null)
                .deletedDt(null)
                .build();

        em.persist(user);
        em.flush();
        em.clear();

        User retrievedUser = em.find(User.class, user.getId());

        System.out.println("retrievedUser.getAgeRange() = " + retrievedUser.getAgeRange()); // AGE_1_9
        System.out.println("retrievedUser.getGender() = " + retrievedUser.getGender()); // male

        assertNotNull(retrievedUser);
        assertEquals(UserAgeRange.AGE_1_9, retrievedUser.getAgeRange());
        assertEquals(UserGender.male, retrievedUser.getGender());
    }
}