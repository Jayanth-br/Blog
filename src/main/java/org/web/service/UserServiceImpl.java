package org.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.web.Exception.Emx;
import org.web.Exception.ValidationException;
import org.web.dto.User;
import org.web.repository.UserRepository;
import org.web.response.Response;
import org.web.uploadImg.UploadProperties;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UploadProperties uploadProperties;

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
            Emx.throwIfPasswordNotValid(user.getPassword(), "Password can't be empty & must have 8 characters!");
            Emx.throwIfAlreadyExists(userRepo.existsByName(user.getName()), "User Name is already present!");
            Emx.throwIfAlreadyExists(userRepo.existsByEmail(user.getEmail()), "Provided Email is already registered!");
            user.setCreatedOn(new Date());
            user.setUpdatedOn(new Date());
            if(profilePicture != null && !profilePicture.isEmpty()){
                String uniqueFileName = UUID.randomUUID().toString() + "." + getFileExtension(profilePicture.getName());
                Path filePath = uploadDirectory.resolve(uniqueFileName);
                Files.createDirectories(uploadDirectory);
                Files.copy(profilePicture.getInputStream(), filePath);
                user.setProfilePicture(filePath.toString());
            }
            user = userRepo.save(user);
            Response response = new Response(true, user, HttpStatus.CREATED.value());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ValidationException e){
            Response errorRes = new Response(false, e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
        }
    }

    public ResponseEntity<Response> updateUser(User user){
        try{
            Emx.throwIfEmpty(user.getName(), "User Name can't be empty!");
            Emx.throwIfEmpty(user.getEmail(), "Email Id can't be empty!");
            Emx.throwIfPasswordNotValid(user.getPassword(), "Password can't be empty & must have 8 characters!");
            user.setUpdatedOn(new Date());
            user = userRepo.save(user);
            Response response = new Response(true, user, HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (ValidationException e){
            Response errResponse = new Response(false, e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResponse);
        }
    }

    public String getFileExtension(String fileName){
        if(fileName == null || fileName.isEmpty()){
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
