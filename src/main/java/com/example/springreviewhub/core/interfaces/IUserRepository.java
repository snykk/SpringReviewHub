package com.example.springreviewhub.core.interfaces;

import com.example.springreviewhub.core.domain.UserDomain;

import java.util.Optional;

public interface IUserRepository {
    Optional<UserDomain> findByUsername(String username);

    UserDomain save(UserDomain user);
}
