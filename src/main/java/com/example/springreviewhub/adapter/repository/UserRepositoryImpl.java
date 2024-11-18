package com.example.springreviewhub.adapter.repository;

import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.interfaces.repositories.IUserRepository;
import com.example.springreviewhub.infrastructure.database.entity.User;
import com.example.springreviewhub.infrastructure.database.entity.mapper.UserMapper;
import com.example.springreviewhub.infrastructure.database.jpa.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<UserDomain> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(UserMapper::toDomain);
    }

    @Override
    public Optional<UserDomain> findById(Long id) {

        return userJpaRepository.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public List<UserDomain> findAll() {

        return userJpaRepository.findAll().stream().map(UserMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public UserDomain save(UserDomain user) {
        User userEntity = UserMapper.fromDomain(user);
        User savedEntity = userJpaRepository.save(userEntity);

        return UserMapper.toDomain(savedEntity);
    }

    @Override
    public void delete(Long id) {

        userJpaRepository.deleteById(id);
    }

    @Override
    public Optional<UserDomain> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserMapper::toDomain);
    }
}
