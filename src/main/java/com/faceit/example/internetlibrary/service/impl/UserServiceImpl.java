package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.exception.ApiRequestException;
import com.faceit.example.internetlibrary.exception.ResourceAlreadyExists;
import com.faceit.example.internetlibrary.model.Role;
import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.repository.RoleRepository;
import com.faceit.example.internetlibrary.repository.UserRepository;
import com.faceit.example.internetlibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getAllUserByUsername(User user) {
        boolean isEmployee = isEmployee(user.getRoles());
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
        User user = null;
        Optional<User> optionalReader = userRepository.findById(id);
        if (optionalReader.isPresent()) {
            user = optionalReader.get();
        }
        return user;
    }

    @Override
    public User addUser(User newUser) {
        checkUsername(newUser.getUserName());

        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setEnabled(true);
        newUser.setRoles(new HashSet<>(Collections.singletonList(new Role(2, "ROLE_USER"))));
        return userRepository.save(newUser);
    }

    @Override
    public User addUser(User newUser, Set<Role> roles) {
        checkUsername(newUser.getUserName());

        boolean isEmployee = isEmployee(roles);
        if (isEmployee) {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setEnabled(true);
            Role userRole = roleRepository.findByName("ROLE_USER");
            newUser.setRoles(new HashSet<>(Collections.singletonList(userRole)));
            return userRepository.save(newUser);
        } else {
            throw new ApiRequestException("user not add");
        }
    }

    @Override
    public User updateUserById(User updateUser, long id) {
        checkUsername(updateUser.getUserName());

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
    public void deleteUserById(long id, Set<Role> roles) {
        boolean isEmployee = isEmployee(roles);
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
            throw new ResourceAlreadyExists("user exists", username);
        }
    }

    private boolean isEmployee(Set<Role> roles) {
        return roles.stream().anyMatch(role -> role.getName().equals("ROLE_EMPLOYEE"));
    }
}
