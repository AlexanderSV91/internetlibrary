package com.faceit.example.internetlibrary.service;

import com.faceit.example.internetlibrary.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUserByUsername(String username);

    User getUserById(long id);

    User addUser(User newUser);

    User updateUserById(User updateUser, long id);

    void deleteUserById(long id);

    User findUserByUserName(String username);
}
