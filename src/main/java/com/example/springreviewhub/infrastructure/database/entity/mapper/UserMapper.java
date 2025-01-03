package com.example.springreviewhub.infrastructure.database.entity.mapper;

import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.infrastructure.database.entity.User;

import java.util.List;
import java.util.stream.Collectors;

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
    public static UserDomain fromEntityToDomain(User user, boolean isPopulateReview) {
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
                .setUpdatedAt(user.getUpdatedAt())
                .setIsActive(user.isActive())
                .setLastLoginAt(user.getLastLoginAt())
                .setFailedLoginAttempts(user.getFailedLoginAttempts())
                .setPhoneNumber(user.getPhoneNumber())
                .setAddress(user.getAddress())
                .setDateOfBirth(user.getDateOfBirth())
                .setEmailVerified(user.isEmailVerified())
                .setBio(user.getBio())
                .setDeletedAt(user.getDeletedAt())

                .setReviews(isPopulateReview ?
                                ReviewMapper.fromEntityListToDomList(user.getReviews(), false, false)
                                : null);
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
    public static User fromDomainToEntity(UserDomain userDomain) {
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
                .setUpdatedAt(userDomain.getUpdatedAt())
                .setIsActive(userDomain.isActive())
                .setLastLoginAt(userDomain.getLastLoginAt())
                .setFailedLoginAttempts(userDomain.getFailedLoginAttempts())
                .setPhoneNumber(userDomain.getPhoneNumber())
                .setAddress(userDomain.getAddress())
                .setDateOfBirth(userDomain.getDateOfBirth())
                .setEmailVerified(userDomain.isEmailVerified())
                .setBio(userDomain.getBio())
                .setDeletedAt(userDomain.getDeletedAt());
    }

    public static List<UserDomain> fromEntityListToDomList(List<User> users, boolean isPopulateReview) {
        return users.stream()
                .map(user -> UserMapper.fromEntityToDomain(user, isPopulateReview))
                .collect(Collectors.toList());
    }
}
