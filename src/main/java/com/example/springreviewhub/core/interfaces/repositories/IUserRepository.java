package com.example.springreviewhub.core.interfaces.repositories;

import com.example.springreviewhub.core.domain.UserDomain;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    Optional<UserDomain> findByUsername(String username);
    Optional<UserDomain> findById(Long id);
    Optional<UserDomain> findByEmail(String email);
    List<UserDomain> findAll();
    UserDomain save(UserDomain user);
    void delete(Long id);
}
