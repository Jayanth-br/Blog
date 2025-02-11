package org.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web.dto.User;
import org.web.response.Response;
import org.web.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    private ResponseEntity<Response> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    private ResponseEntity<Response> updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
}
