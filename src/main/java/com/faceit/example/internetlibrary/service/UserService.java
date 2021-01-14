package com.faceit.example.internetlibrary.service;

import com.faceit.example.internetlibrary.model.Role;
import com.faceit.example.internetlibrary.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAllUserByUsername(User user);

    User getUserById(long id);

    User addUser(User newUser);

    User addUser(User newUser, Set<Role> roles);

    User updateUserById(User updateUser, long id);

    void deleteUserById(long id, Set<Role> roles);

    User findUserByUserName(String username);

    boolean existsUserByUserName(String username);
}
