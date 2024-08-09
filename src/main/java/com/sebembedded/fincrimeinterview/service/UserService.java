package com.sebembedded.fincrimeinterview.service;

import com.sebembedded.fincrimeinterview.model.User;
import com.sebembedded.fincrimeinterview.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveOrUpdate(User user) {
        var userEntity = user;
        if (user.id() == null) {
            userEntity = new User(UUID.randomUUID().toString(), user.firstName(), user.lastName(),
                    user.occupation());
        }
        return userRepository.save(userEntity);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Set<User> query(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}
