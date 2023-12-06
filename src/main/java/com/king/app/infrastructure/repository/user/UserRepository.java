package com.king.app.infrastructure.repository.user;

import com.king.app.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByProviderId(String providerId);
}
