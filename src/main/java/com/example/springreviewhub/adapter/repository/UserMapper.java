package com.example.springreviewhub.adapter.repository;

import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.infrastructure.entity.UserEntity;

public class UserMapper {
    public static UserDomain toDomain(UserEntity entity) {
        return new UserDomain(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getPassword(), entity.getRole());
    }

    public static UserEntity toEntity(UserDomain domain) {
        return new UserEntity(domain.getId(), domain.getUsername(), domain.getEmail(), domain.getPassword(), domain.getRole(), null, null);
    }
}

