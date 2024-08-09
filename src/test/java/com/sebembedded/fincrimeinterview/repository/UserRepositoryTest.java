package com.sebembedded.fincrimeinterview.repository;

import com.sebembedded.fincrimeinterview.model.User;
import com.sebembedded.fincrimeinterview.model.enumeration.Occupation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    void saveNewUser() {
        var user = userRepository.save(new User("test", "test", "test", Occupation.EMPLOYED));

        assertEquals("test", user.id());
    }

    @Test
    void findNonExistingUserById() {
        assertTrue(userRepository.findById("test").isEmpty());
    }

    @Test
    void findExistingUserById() {
        userRepository.save(new User("test", "test", "test", Occupation.UNEMPLOYED));
        assertTrue(userRepository.findById("test").isPresent());
    }

    @Test
    void query() {
        assertThrows(UnsupportedOperationException.class, () -> userRepository.findByFirstNameAndLastName("test", "test"));
    }
}