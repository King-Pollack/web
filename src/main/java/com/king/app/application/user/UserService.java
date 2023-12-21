package com.king.app.application.user;

import com.king.app.domain.user.User;

public interface UserService {

    User findById(Long userId);
}
