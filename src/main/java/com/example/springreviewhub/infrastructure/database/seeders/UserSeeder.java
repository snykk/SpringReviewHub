package com.example.springreviewhub.infrastructure.database.seeders;

import com.example.springreviewhub.core.domain.Role;
import com.example.springreviewhub.infrastructure.database.entity.User;
import com.example.springreviewhub.infrastructure.database.jpa.UserJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * A seeder class for preloading user data into the database.
 * <p>
 * This class implements the {@link CommandLineRunner} interface, allowing it to execute specific
 * logic after the application context is initialized. It checks if the user table is empty,
 * and if so, preloads it with predefined admin and reviewer accounts.
 * </p>
 * <p>
 * The preloaded users include attributes such as username, email, password, role, and additional
 * metadata like bio, address, and date of birth.
 * </p>
 */
@Component
public class UserSeeder implements CommandLineRunner {

    private final UserJpaRepository userJpaRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new instance of {@code UserSeeder}.
     *
     * @param userJpaRepository the repository used for interacting with the user table
     * @param passwordEncoder   the encoder used for securely hashing user passwords
     */
    public UserSeeder(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Executes the logic to seed initial user data into the database.
     * <p>
     * If the database is empty, this method creates and saves two user accounts:
     * an admin account and a reviewer account. Passwords are securely hashed using the
     * provided {@code PasswordEncoder}.
     * </p>
     *
     * @param args command-line arguments (not used in this implementation)
     * @throws Exception if an error occurs during execution
     */
    @Override
    public void run(String... args) throws Exception {
        if (userJpaRepository.count() == 0) {
            User admin = new User()
                    .setUsername("najibfikri")
                    .setEmail("najibfikri26@gmail.com")
                    .setPassword(passwordEncoder.encode("12345678"))
                    .setRole(Role.Admin)
                    .setIsActive(true)
                    .setFailedLoginAttempts(0)
                    .setEmailVerified(true)
                    .setPhoneNumber("081234567890")
                    .setAddress("Jl kerang no 69")
                    .setDateOfBirth(LocalDate.of(1800, 1, 1))
                    .setBio("Seorang admin biasa")
                    .setCreatedAt(LocalDateTime.now())
                    .setUpdatedAt(LocalDateTime.now());

            User reviewer = new User()
                    .setUsername("snykk")
                    .setEmail("snykk@gmail.com")
                    .setPassword(passwordEncoder.encode("12345678"))
                    .setRole(Role.Reviewer)
                    .setIsActive(true)
                    .setFailedLoginAttempts(0)
                    .setEmailVerified(true)
                    .setPhoneNumber("089876543210")
                    .setAddress("Jl kerang no 24")
                    .setDateOfBirth(LocalDate.of(1801, 1, 1))
                    .setBio("Seorang reviewer tidak biasa")
                    .setCreatedAt(LocalDateTime.now())
                    .setUpdatedAt(LocalDateTime.now());

            userJpaRepository.saveAll(Arrays.asList(admin, reviewer));

            System.out.println("[*] Data user berhasil di-seed");
        }
    }
}
