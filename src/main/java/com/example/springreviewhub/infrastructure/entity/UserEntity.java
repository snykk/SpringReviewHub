package com.example.springreviewhub.infrastructure.entity;

import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.domain.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@ToString
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)  // Make sure role is stored as a string representation of the enum
    @Column(nullable = false)
    private Role role; // Use enum Role

    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE) // Prevent modification from outside
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column()
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.updatedAt == null) {
            this.updatedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Convert `UserEntity` to `UserDomain`.
     */
    public UserDomain toDomain() {
        return new UserDomain(
                this.id,
                this.username,
                this.email,
                this.password,
                this.role, // Directly return enum role
                this.createdAt,
                this.updatedAt
        );
    }

    /**
     * Convert `UserDomain` to `UserEntity`.
     */
    public static UserEntity fromDomain(UserDomain userDomain) {
        return new UserEntity(
                userDomain.getId(),
                userDomain.getUsername(),
                userDomain.getEmail(),
                userDomain.getPassword(),
                userDomain.getRole(), // Pass the Role enum
                null,
                null
        );
    }
}
