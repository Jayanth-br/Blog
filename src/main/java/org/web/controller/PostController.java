package org.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.web.dto.Post;
import org.web.dtoVo.PostVo;
import org.web.response.Response;
import org.web.service.PostService;

import java.io.IOException;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response> createPost(@RequestPart("post") PostVo post, @RequestPart("postPic")MultipartFile postPic) throws IOException {
        return postService.createPost(post, postPic);
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response> updatePost(@RequestPart("post")PostVo post, @RequestPart("postPic")MultipartFile postPic) throws IOException {
        return postService.updatePost(post, postPic);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response> findPostById(@PathVariable(value = "id") long id){
        return postService.findPostById(id);
    }

}
