package com.example.demo.repository;

import com.example.demo.models.User;
import com.example.demo.models.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByLogin() {
        String login = "admin";
        User user = new User(login, "1234", UserRole.ADMIN);
        userRepository.save(user);

        String loginFound = userRepository.findByLogin(login).getUsername();

        assertThat(loginFound).isEqualTo("admin");

    }

    @Test
    void findByLoginFail() {
        String login = "admin";
        String loginFail = "user";
        User user = new User(login, "1234", UserRole.ADMIN);
        userRepository.save(user);

        String loginFound = userRepository.findByLogin(login).getUsername();

        assertThat(loginFound).isNotEqualTo(loginFail);
    }

}