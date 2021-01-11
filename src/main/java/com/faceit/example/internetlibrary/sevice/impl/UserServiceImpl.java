package com.faceit.example.internetlibrary.sevice.impl;

import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.repository.UserRepository;
import com.faceit.example.internetlibrary.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        User user = null;
        Optional<User> optionalReader = userRepository.findById(id);
        if (optionalReader.isPresent()) {
            user = optionalReader.get();
        }
        return user;
    }

    @Override
    public User addUser(User newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public User updateUserById(User updateUser, long id) {
        User reader = getUserById(id);
        if (reader != null) {
            updateUser.setId(id);
        }
        userRepository.save(updateUser);
        return updateUser;
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }
}
