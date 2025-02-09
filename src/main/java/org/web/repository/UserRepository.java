package org.web.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.web.dto.User;

import javax.persistence.EntityManager;

@Repository
public class UserRepository {

    @Autowired
    private EntityManager entityManager;

    public User createUser(User user){
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();
        return user;
    }

    public User findById(long id){
       User user = entityManager.find(User.class, id);
       entityManager.flush();
       entityManager.clear();
       return user;
    }

}
