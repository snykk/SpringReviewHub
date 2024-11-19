package com.example.springreviewhub.core.interfaces.usecases;

import com.example.springreviewhub.core.domain.UserDomain;

import java.util.List;

public interface IUserUseCase {
    UserDomain getAuthenticatedUser(String username);
    List<UserDomain> getAllUsers();
    UserDomain getUserById(Long id);
    UserDomain updateUser(Long userId, UserDomain updatedUser);
    void deleteUser(Long userId);
    void changePassword(Long userId, String oldPassword, String newPassword);
    UserDomain changeEmail(Long userId, String newEmail);

}
