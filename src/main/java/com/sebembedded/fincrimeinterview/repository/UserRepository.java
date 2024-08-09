package com.sebembedded.fincrimeinterview.repository;

import com.sebembedded.fincrimeinterview.model.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private final Map<String, User> userStore = new ConcurrentHashMap<>();

    public User save(User user) {
        userStore.put(user.id(), user);
        return user;
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(userStore.get(id));
    }

    public Set<User> findByFirstNameAndLastName(String firstName, String lastName) {
        return userStore.values()
                .stream()
                .filter(user -> user.firstName().equalsIgnoreCase(firstName) && user.lastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toSet());
    }
}
