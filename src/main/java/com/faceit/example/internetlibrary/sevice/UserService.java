package com.faceit.example.internetlibrary.sevice;

import com.faceit.example.internetlibrary.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();

    User getUserById(long id);

    User addUser(User newReader);

    User updateUserById(User updateReader, long id);

    void deleteUserById(long id);
}
