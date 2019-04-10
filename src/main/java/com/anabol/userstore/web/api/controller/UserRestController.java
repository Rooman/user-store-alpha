package com.anabol.userstore.web.api.controller;

import com.anabol.userstore.entity.User;
import com.anabol.userstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public List<User> getAll() {
        return userService.findAll();
    }

    @PostMapping(value = "/users")
    public void addUser(@RequestBody User user) {
        userService.insert(user);
    }
}
