package com.edu.inatel.br.controller;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.inatel.br.model.User;
import com.edu.inatel.br.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/internal/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @PutMapping("/preferences")
    public ResponseEntity<User> updatePreferences(Principal principal, @RequestBody List<String> categories) throws ExecutionException, InterruptedException {
        String userId = principal.getName();
        return ResponseEntity.ok(userService.updatePreferredCategories(userId, categories));
    }
}