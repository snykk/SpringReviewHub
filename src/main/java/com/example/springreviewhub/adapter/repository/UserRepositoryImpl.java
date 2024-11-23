package com.example.springreviewhub.adapter.repository;

import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.interfaces.repositories.IUserRepository;
import com.example.springreviewhub.infrastructure.database.entity.User;
import com.example.springreviewhub.infrastructure.database.entity.mapper.UserMapper;
import com.example.springreviewhub.infrastructure.database.jpa.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<UserDomain> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(user -> UserMapper.fromEntityToDomain(user, false));
    }

    @Override
    public Optional<UserDomain> findById(Long id) {
        return userJpaRepository.findById(id)
                .map(user -> UserMapper.fromEntityToDomain(user, false));
    }

    @Override
    public Optional<UserDomain> findByIdWithRole(Long id, String role) {
        return userJpaRepository.findByIdWithRole(id, role)
                .map(user -> UserMapper.fromEntityToDomain(user, false));
    }

    @Override
    public List<UserDomain> findAllWithRole(String role) {
        System.out.println("masih jalan kah");
        List<User> user = userJpaRepository.findAll();

        System.out.println(user);
        List<User> userEntity = userJpaRepository.findAllWithRole(role);

        System.out.println("ini user entity");
        System.out.println(userEntity);

        return UserMapper.fromEntityListToDomList(userEntity, false);
    }

    @Override
    public UserDomain save(UserDomain user) {
        User userEntity = UserMapper.fromDomainToEntity(user);
        User savedEntity = userJpaRepository.save(userEntity);
        return UserMapper.fromEntityToDomain(savedEntity, false);
    }

    @Override
    public void softDelete(Long id) {
        userJpaRepository.softDeleteUser(id);
    }

    @Override
    public Optional<UserDomain> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(user -> UserMapper.fromEntityToDomain(user, false));
    }
}
