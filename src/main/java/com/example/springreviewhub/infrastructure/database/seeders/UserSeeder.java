package com.example.springreviewhub.infrastructure.database.seeders;

import com.example.springreviewhub.core.domain.Role;
import com.example.springreviewhub.infrastructure.database.entity.User;
import com.example.springreviewhub.infrastructure.database.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserSeeder implements CommandLineRunner {

    private final UserJpaRepository userJpaRepository;

    private final PasswordEncoder passwordEncoder;

    public UserSeeder(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userJpaRepository.count() == 0) {
            User admin = new User()
                    .setUsername("najibfikri")
                    .setEmail("najibfikri26@gmail.com")
                    .setPassword(passwordEncoder.encode("12345678"))
                    .setRole(Role.Admin);

            User reviewer = new User()
                    .setUsername("snykk")
                    .setEmail("snykk@gmail.com")
                    .setPassword(passwordEncoder.encode("12345678"))
                    .setRole(Role.Reviewer);



            userJpaRepository.saveAll(Arrays.asList(admin, reviewer));

            System.out.println("[*] Data user berhasil di-seed");
        }
    }
}
