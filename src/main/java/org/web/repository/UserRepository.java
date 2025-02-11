package org.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.web.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
