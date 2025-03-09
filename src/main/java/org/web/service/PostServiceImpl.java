package org.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.web.dto.Category;
import org.web.dto.Post;
import org.web.dto.User;
import org.web.dtoVo.PostVo;
import org.web.exception.Emx;
import org.web.exception.ValidationException;
import org.web.repository.CategoryRepository;
import org.web.repository.PostRepository;
import org.web.repository.UserRepository;
import org.web.response.Response;
import org.web.upload.PostUploadProperties;
import org.web.upload.UploadFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
@Transactional
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PostUploadProperties uploadProperties;

    private Path uploadDirectory;

    @PostConstruct
    public void init(){
        this.uploadDirectory = Paths.get(uploadProperties.getDirectory());
    }

    @Override
    public ResponseEntity<Response> createPost(PostVo post, MultipartFile postPic) throws IOException {
        try{
            Emx.throwIfEmpty(post.getTitle(), "Post title can't be empty!");
            Emx.throwIfEmpty(post.getContent(), "Post must have content!");
            Emx.throwIfNullOrZero(post.getCreatedBy(), "Created By can't be null or zero!");
            Emx.throwIfNullOrZero(post.getCategory(), "CategoryID can't be null or zero!");

            Category category = categoryRepo.findById(post.getCategory()).orElseThrow(() -> new ValidationException("Category not found!"));
            User user = userRepo.findById(post.getCreatedBy()).orElseThrow(() -> new ValidationException("User not found!"));

            Post newPost = new Post();
            newPost.setTitle(post.getTitle());
            newPost.setContent(post.getContent());
            newPost.setCategory(category);
            newPost.setPicture(UploadFactory.uploadToLocalSystem(postPic, uploadDirectory));
            newPost.setCreatedBy(user);
            newPost.setCreatedOn(new Date());
            newPost.setUpdatedOn(new Date());
            Post savedPost = postRepo.save(newPost);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response(true, savedPost, HttpStatus.CREATED.value()));
        }catch (ValidationException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    public ResponseEntity<Response> updatePost(PostVo post, MultipartFile postPic) throws IOException {
        try{
            Emx.throwIfEmpty(post.getTitle(), "Post title can't be empty!");
            Emx.throwIfEmpty(post.getContent(), "Post must have content!");
            Emx.throwIfNullOrZero(post.getCategory(), "CategoryID can't be null or zero!");
            Post existingPost = postRepo.findById(post.getId());
            Category category = categoryRepo.findById(post.getCategory()).orElseThrow(() -> new ValidationException("Category can't be empty!."));
            if(postPic != null && !postPic.isEmpty()){
                String oldPic = existingPost.getPicture();
                UploadFactory.deleteExistingProfilePicture(oldPic);
            }
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            existingPost.setPicture(UploadFactory.uploadToLocalSystem(postPic, uploadDirectory));
            existingPost.setCategory(category);
            existingPost.setUpdatedOn(new Date());
            Post updatedPost = postRepo.save(existingPost);
            return ResponseEntity.ok(new Response(true, updatedPost, HttpStatus.OK.value()));
        } catch (ValidationException e){
            return ResponseEntity.badRequest().body(new Response(false, e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @Override
    public ResponseEntity<Response> findPostById(long id) {
        Post post = postRepo.findById(id);
        if(post != null){
            return ResponseEntity.ok(new Response(true, post, HttpStatus.OK.value()));
        }
        return ResponseEntity.badRequest().body(new Response(false, "Post with Post ID: " + id + " not found!", HttpStatus.BAD_REQUEST.value()));
    }
}
