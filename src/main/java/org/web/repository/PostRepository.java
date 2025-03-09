package org.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.web.dto.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findById(long id);

}
