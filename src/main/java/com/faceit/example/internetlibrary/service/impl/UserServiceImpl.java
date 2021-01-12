package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.model.Role;
import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.repository.UserRepository;
import com.faceit.example.internetlibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getAllUserByUsername(String username) {
        List<User> users;
        User user = userRepository.findUserByUserName(username);
        boolean isEmployee = usernameIsEmployee(user);
        if (isEmployee) {
            users = userRepository.findAll();
        } else {
            users = Collections.singletonList(user);
        }
        return users;
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
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setEnabled(true);
        newUser.setRoles(new HashSet<>(Collections.singletonList(new Role(2, "ROLE_USER"))));
        return userRepository.save(newUser);
    }

    @Override
    public User updateUserById(User updateUser, long id) {
        User user = getUserById(id);
        if (user != null) {
            updateUser.setId(id);
            updateUser.setRoles(user.getRoles());
            updateUser.setEnabled(user.isEnabled());
            if (updateUser.getPassword() != null) {
                updateUser.setPassword(bCryptPasswordEncoder.encode(updateUser.getPassword()));
            } else {
                updateUser.setPassword(user.getPassword());
            }
        }
        userRepository.save(updateUser);
        return updateUser;
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUserByUserName(String username) {
        return userRepository.findUserByUserName(username);
    }

    private boolean usernameIsEmployee(User user) {
        boolean isEmployee = false;
        if (user != null) {
            isEmployee = user.getRoles()
                    .stream()
                    .findFirst()
                    .filter(role -> role.getName().equals("ROLE_EMPLOYEE"))
                    .isPresent();
        }
        return isEmployee;
    }


}
