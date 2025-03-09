package org.web.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.web.dto.User;
import org.web.response.Response;
import java.io.IOException;

public interface UserService {

    public ResponseEntity<Response> createUser(User user, MultipartFile profilePicture) throws IOException;

    public ResponseEntity<Response> updateUser(User user, MultipartFile profilePicture) throws IOException;

    public ResponseEntity<Response> findUserById(long id);

}
