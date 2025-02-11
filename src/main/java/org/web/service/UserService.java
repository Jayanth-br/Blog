package org.web.service;

import org.springframework.http.ResponseEntity;
import org.web.dto.User;
import org.web.response.Response;

public interface UserService {

    public ResponseEntity<Response> createUser(User user);

    public ResponseEntity<Response> updateUser(User user);

}
