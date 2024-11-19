package com.example.springreviewhub.infrastructure.database.entity.mapper;

import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.infrastructure.database.entity.User;

public class UserMapper {

    /**
     * Convert `UserEntity` to `UserDomain`.
     *
     * @param user the `User` entity retrieved from the database
     * @return a `UserDomain` object representing the domain-level user model
     *
     * This method is used to convert database entities into domain models,
     * which are utilized within the core application logic. It maps all
     * relevant fields from the entity to the domain.
     */
    public static UserDomain toDomain(User user) {
        if (user == null) {
            return null;
        }
        return new UserDomain()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setRole(user.getRole())
                .setCreatedAt(user.getCreatedAt())
                .setUpdatedAt(user.getUpdatedAt());
    }

    /**
     * Convert `UserDomain` to `UserEntity`.
     *
     * @param userDomain the `UserDomain` object containing domain data
     * @return a `User` entity to be persisted in the database
     *
     * This method is used to convert domain models into database entities.
     * It is typically used in persistence operations like saving or updating
     * user records in the database.
     */
    public static User fromDomain(UserDomain userDomain) {
        if (userDomain == null) {
            return null;
        }
        return new User()
                .setId(userDomain.getId())
                .setUsername(userDomain.getUsername())
                .setEmail(userDomain.getEmail())
                .setPassword(userDomain.getPassword())
                .setRole(userDomain.getRole())
                .setCreatedAt(userDomain.getCreatedAt())
                .setUpdatedAt(userDomain.getUpdatedAt());
    }
}
