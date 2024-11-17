package com.example.springreviewhub.core.interfaces.usecases;

import com.example.springreviewhub.core.domain.UserDomain;

public interface IUserUseCase {
    UserDomain getAuthenticatedUser(String username);
}
