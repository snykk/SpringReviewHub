package com.example.springreviewhub.adapter.repository;

import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.interfaces.repositories.IUserRepository;
import com.example.springreviewhub.infrastructure.database.entity.User;
import com.example.springreviewhub.infrastructure.database.jpa.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<UserDomain> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(User::toDomain);
    }

    @Override
    public UserDomain save(UserDomain user) {
        User savedEntity = userJpaRepository.save(User.fromDomain(user));
        return savedEntity.toDomain();
    }
}
