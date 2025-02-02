package org.web.dto;

import javax.persistence.ManyToMany;
import java.util.List;

public class Tag {

    private long id;
    private String name;
    @ManyToMany(mappedBy = "tags")
    private List<Post> posts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
