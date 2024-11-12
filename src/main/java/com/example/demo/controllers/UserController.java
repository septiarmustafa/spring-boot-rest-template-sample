package com.example.demo.controllers;

import com.example.demo.constant.AppPath;
import com.example.demo.dto.CommonResponse;
import com.example.demo.entities.User;
import com.example.demo.mapper.ResponseMapper;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.USERS)
public class UserController {

    @Autowired
    private UserService userService;
    private final ResponseMapper responseMapper;


    @PostMapping
    public ResponseEntity<CommonResponse<User>> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseMapper.toSuccessResponse(createdUser, null, "User created successfully"));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(responseMapper.toSuccessResponse(users, null, "Successfully fetched all users"));
    }

    @GetMapping(AppPath.ID)
    public ResponseEntity<CommonResponse<Optional<User>>> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return ResponseEntity.ok(responseMapper.toResponse(user, null));
    }

    @PutMapping(AppPath.ID)
    public ResponseEntity<CommonResponse<User>> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            User user = userService.updateUser(id, updatedUser);
            return ResponseEntity.ok(responseMapper.toSuccessResponse(user, null, "User updated successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(responseMapper.toErrorResponse("User not found"));
        }
    }

    @DeleteMapping(AppPath.ID)
    public ResponseEntity<CommonResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(responseMapper.toSuccessResponse(null, null, "User deleted successfully"));
    }
}

