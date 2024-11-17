package com.example.springreviewhub.infrastructure.entity;

import com.example.springreviewhub.core.domain.UserDomain;
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

    @Column(nullable = false)
    private String role; // Roles: "ROLE_ADMIN", "ROLE_REVIEWER"

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
                this.role
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
                userDomain.getRole(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
