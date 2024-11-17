package com.example.springreviewhub.adapter.repository;

import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.interfaces.IUserRepository;
import com.example.springreviewhub.infrastructure.entity.UserEntity;
import com.example.springreviewhub.infrastructure.jpa.UserJpaRepository;
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
        return userJpaRepository.findByUsername(username).map(UserEntity::toDomain);
    }

    @Override
    public UserDomain save(UserDomain user) {
        UserEntity savedEntity = userJpaRepository.save(UserEntity.fromDomain(user));
        return savedEntity.toDomain();
    }
}
