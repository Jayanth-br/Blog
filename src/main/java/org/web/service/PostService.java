package org.web.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.web.dto.Post;
import org.web.dtoVo.PostVo;
import org.web.response.Response;

import java.io.IOException;

public interface PostService {

    public ResponseEntity<Response> createPost(PostVo post, MultipartFile postPic) throws IOException;

    public ResponseEntity<Response> updatePost(PostVo post, MultipartFile postPic) throws IOException;

    public ResponseEntity<Response> findPostById(long id);

}
