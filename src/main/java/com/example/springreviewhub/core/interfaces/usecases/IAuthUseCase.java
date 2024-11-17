package com.example.springreviewhub.core.interfaces.usecases;

import com.example.springreviewhub.core.domain.UserDomain;

public interface IAuthUseCase {
    UserDomain register(UserDomain user);
    String authenticate(UserDomain user);
    UserDomain getAuthenticatedUser(String username);
}
