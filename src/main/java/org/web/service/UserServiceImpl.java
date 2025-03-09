package org.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.web.exception.Emx;
import org.web.exception.ValidationException;
import org.web.dto.User;
import org.web.repository.UserRepository;
import org.web.response.Response;
import org.web.upload.UploadFactory;
import org.web.upload.UserUploadProperties;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserUploadProperties uploadProperties;

    private Path uploadDirectory;

    @PostConstruct
    public void init(){
        this.uploadDirectory = Paths.get(uploadProperties.getDirectory());
    }

    @Override
    public ResponseEntity<Response> createUser(User user, MultipartFile profilePicture) throws IOException {
        try{
            Emx.throwIfEmpty(user.getName(), "User Name can't be empty!");
            Emx.throwIfEmpty(user.getEmail(), "Email Id can't be empty!");
            Emx.throwIfPasswordNotValid(user.getPassword(), "Password can't be empty & must have minimum 8 characters!");
            Emx.throwIfAlreadyExists(userRepo.existsByName(user.getName()), "User Name is already present!");
            Emx.throwIfAlreadyExists(userRepo.existsByEmail(user.getEmail()), "Provided Email is already registered!");
            user.setCreatedOn(new Date());
            user.setUpdatedOn(new Date());
            user.setProfilePicture(UploadFactory.uploadToLocalSystem(profilePicture, uploadDirectory));
            user = userRepo.save(user);
            Response response = new Response(true, user, HttpStatus.CREATED.value());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ValidationException e){
            Response errorRes = new Response(false, e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
        }
    }

    public ResponseEntity<Response> updateUser(User user, MultipartFile profilePicture) throws IOException {
        try {
            Emx.throwIfEmpty(user.getName(), "User Name can't be empty!");
            Emx.throwIfEmpty(user.getEmail(), "Email Id can't be empty!");
            Emx.throwIfPasswordNotValid(user.getPassword(), "Password can't be empty & must have minimum 8 characters!");
            user.setUpdatedOn(new Date());
            if(user.getId() > 0){
                User existingUser = userRepo.findById(user.getId());
                String oldProfilePicture = existingUser.getProfilePicture();
                if(oldProfilePicture != null && !oldProfilePicture.isEmpty()){
                    UploadFactory.deleteExistingProfilePicture(oldProfilePicture);
                }
            }
            user.setProfilePicture(UploadFactory.uploadToLocalSystem(profilePicture, uploadDirectory));
            user = userRepo.save(user);
            Response response = new Response(true, user, HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ValidationException e){
            Response errResponse = new Response(false, e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResponse);
        }
    }

    @Override
    public ResponseEntity<Response> findUserById(long id) {
        User user = userRepo.findById(id);
        if(user != null && user.getId() > 0){
            Response response = new Response(true, user, HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "User with ID: " + id + " not found!", HttpStatus.BAD_REQUEST.value()));
        }
    }
}
