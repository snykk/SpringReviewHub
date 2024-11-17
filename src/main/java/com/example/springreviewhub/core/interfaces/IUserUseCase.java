package com.example.springreviewhub.core.interfaces;

import com.example.springreviewhub.core.domain.UserDomain;

public interface IUserUseCase {
    UserDomain getAuthenticatedUser(String username);
}
