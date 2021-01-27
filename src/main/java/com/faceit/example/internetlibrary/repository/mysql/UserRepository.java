package com.faceit.example.internetlibrary.repository.mysql;

import com.faceit.example.internetlibrary.model.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserName(String username);

    boolean existsUserByUserName(String username);

    boolean existsByEmail(String email);
}
