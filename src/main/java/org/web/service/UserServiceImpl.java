package org.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web.Exception.Emx;
import org.web.Exception.ValidationException;
import org.web.dto.User;
import org.web.repository.UserRepository;
import org.web.response.Response;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepo;

    @Transactional
    @Override
    public ResponseEntity<Response> createUser(User user) {
        try{
            Emx.throwIfEmpty(user.getName(), "User Name can't be empty!");
            Emx.throwIfEmpty(user.getEmail(), "Email Id can't be empty!");
            Emx.throwIfEmpty(user.getPassword(), "Password can't be empty!");
            user.setCreatedOn(new Date());
            user.setUpdatedOn(new Date());
            user = userRepo.createUser(user);
            user = userRepo.findById(user.getId());
            Response response = new Response(true, user, HttpStatus.CREATED.value());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (ValidationException e){
            Response errorRes = new Response(false, e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
        }
    }
}
