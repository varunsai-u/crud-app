package com.project2.crudbackend.controller;

import com.project2.crudbackend.model.User;
import com.project2.crudbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserName(@PathVariable String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> l = userRepository.findAll();

        return ResponseEntity.ok(l);

    }

    @PutMapping("/email/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User updated) {
        User exist = userRepository.findByEmail(email);
        if (exist != null) {
            exist.setName(updated.getName());
            exist.setEmail(updated.getEmail());
            return ResponseEntity.ok(userRepository.save(exist));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        User exist = userRepository.findByEmail(email);
        if (exist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
        userRepository.delete(exist);
        return ResponseEntity.noContent().build();
    }

}
