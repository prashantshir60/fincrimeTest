package com.sebembedded.fincrimeinterview.service;

import com.sebembedded.fincrimeinterview.model.User;
import com.sebembedded.fincrimeinterview.model.enumeration.Occupation;
import com.sebembedded.fincrimeinterview.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private UserService userService;

    private static User getUser() {
        return new User("test", "test", "test", Occupation.EMPLOYED);
    }

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void saveNewUser() {
        when(userRepository.save(any(User.class)))
                .thenReturn(new User("test", "test", "test", Occupation.EMPLOYED));

        userService.saveOrUpdate(new User(null, "test", "test", Occupation.EMPLOYED));

        var argumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(argumentCaptor.capture());

        assertNotNull(argumentCaptor.getValue());
        assertNotNull(argumentCaptor.getValue().id());
    }

    @Test
    void updateUser() {
        when(userRepository.save(any(User.class)))
                .thenReturn(getUser());

        userService.saveOrUpdate(getUser());

        var argumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(argumentCaptor.capture());

        assertNotNull(argumentCaptor.getValue());
        assertEquals("test", argumentCaptor.getValue().id());
    }

    @Test
    void findById() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(getUser()));

        assertTrue(userService.findById("test").isPresent());
    }

    @Test
    void query() {
        when(userRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Set.of(getUser()));

        assertFalse(userService.query("test", "test").isEmpty());
    }
}