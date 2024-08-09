package com.sebembedded.fincrimeinterview.controller;

import com.sebembedded.fincrimeinterview.model.User;
import com.sebembedded.fincrimeinterview.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/")
    public User addOrUpdateUser(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        var user = userService.findById(userId);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/find-by-name")
    public ResponseEntity<Set<User>> queryUser(@RequestParam String firstName,
                                               @RequestParam String lastName) {
        return ResponseEntity.ok(userService.query(firstName, lastName));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception e) {
        LOGGER.error("An error occurred", e);
        return ResponseEntity
                .internalServerError()
                .body(Map.of("message", e.getMessage()));
    }
}
