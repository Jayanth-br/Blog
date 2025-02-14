package org.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.web.dto.User;
import org.web.response.Response;
import org.web.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<Response> createUser(@RequestPart("user") User user, @RequestPart("profilePicture") MultipartFile profilePicture) throws IOException {
        return userService.createUser(user, profilePicture);
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<Response> updateUser(@RequestPart("user") User user, @RequestPart("profilePicture") MultipartFile profilePicture) throws IOException {
        return userService.updateUser(user, profilePicture);
    }
}
