package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.exception.ApiRequestException;
import com.faceit.example.internetlibrary.exception.ResourceAlreadyExists;
import com.faceit.example.internetlibrary.exception.ResourceNotFoundException;
import com.faceit.example.internetlibrary.model.Role;
import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.repository.UserRepository;
import com.faceit.example.internetlibrary.service.RoleService;
import com.faceit.example.internetlibrary.service.UserService;
import com.faceit.example.internetlibrary.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleService roleService,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getAllUserByUsername(User user) {
        boolean isEmployee = Utils.isEmployee(user.getRoles());
        List<User> users;
        if (isEmployee) {
            users = userRepository.findAll();
        } else {
            users = Collections.singletonList(userRepository.findUserByUserName(user.getUserName()));
        }
        return users;
    }

    @Override
    public User getUserById(long id) {
        Optional<User> optionalReader = userRepository.findById(id);
        return Utils.getDataFromTypeOptional(optionalReader);
    }

    @Override
    public User addUser(User newUser) {
        checkUsername(newUser.getUserName());
        preparingToAddUser(newUser);
        return userRepository.save(newUser);
    }

    @Override
    public User addUser(User newUser, Set<Role> roles) {
        checkUsername(newUser.getUserName());

        boolean isEmployee = Utils.isEmployee(roles);
        if (isEmployee) {
            preparingToAddUser(newUser);
            return userRepository.save(newUser);
        } else {
            throw new ApiRequestException("user not add");
        }
    }

    @Override
    public User updateUserById(User updateUser, long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User isCurrentUser = Utils.getDataFromTypeOptional(optionalUser);
        if ((updateUser.getId() == isCurrentUser.getId() && !updateUser.getUserName().equals(isCurrentUser.getUserName()) ||
                updateUser.getId() != isCurrentUser.getId() && updateUser.getUserName().equals(isCurrentUser.getUserName()))) {
            checkUsername(updateUser.getUserName());
        }

        User user = getUserById(id);
        if (user != null) {
            updateUser.setId(id);
            updateUser.setRoles(user.getRoles());
            updateUser.setEnabled(user.isEnabled());
            if (updateUser.getPassword() != null && updateUser.getPassword().length() < 30) {
                updateUser.setPassword(bCryptPasswordEncoder.encode(updateUser.getPassword()));
            } else {
                updateUser.setPassword(user.getPassword());
            }
        } else {
            throw new ResourceNotFoundException("Not found");
        }
        userRepository.save(updateUser);
        return updateUser;
    }

    @Override
    public void deleteUserById(long id, Set<Role> roles) {
        boolean isEmployee = Utils.isEmployee(roles);
        if (isEmployee) {
            userRepository.deleteById(id);
        } else {
            throw new ApiRequestException("user not delete");
        }
    }

    @Override
    public User findUserByUserName(String username) {
        return userRepository.findUserByUserName(username);
    }

    @Override
    public boolean existsUserByUserName(String username) {
        return userRepository.existsUserByUserName(username);
    }

    private void checkUsername(String username) {
        boolean checkUser = existsUserByUserName(username);
        if (checkUser) {
            throw new ResourceAlreadyExists("userName", "username exists");
        }
    }

    private void preparingToAddUser(User newUser) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setEnabled(false);
        Role userRole = roleService.findByName("ROLE_USER");
        newUser.setRoles(new HashSet<>(Collections.singletonList(userRole)));
    }
}
