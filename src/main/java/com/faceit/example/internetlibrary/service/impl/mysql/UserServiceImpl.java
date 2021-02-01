package com.faceit.example.internetlibrary.service.impl.mysql;

import com.faceit.example.internetlibrary.dto.request.mysql.UserRequest;
import com.faceit.example.internetlibrary.exception.ApiRequestException;
import com.faceit.example.internetlibrary.exception.ResourceAlreadyExists;
import com.faceit.example.internetlibrary.mapper.mysql.UserMapper;
import com.faceit.example.internetlibrary.model.mysql.NumberAuthorization;
import com.faceit.example.internetlibrary.model.mysql.Role;
import com.faceit.example.internetlibrary.model.mysql.User;
import com.faceit.example.internetlibrary.repository.mysql.UserRepository;
import com.faceit.example.internetlibrary.service.mysql.RoleService;
import com.faceit.example.internetlibrary.service.mysql.UserService;
import com.faceit.example.internetlibrary.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleService roleService,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
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
        checkEmail(newUser.getEmail());
        preparingToAddUser(newUser);
        return userRepository.save(newUser);
    }

    @Override
    public User addUser(User newUser, Set<Role> roles) {
        checkUsername(newUser.getUserName());
        checkEmail(newUser.getEmail());
        boolean isEmployee = Utils.isEmployee(roles);
        if (isEmployee) {
            preparingToAddUser(newUser);
            return userRepository.save(newUser);
        } else {
            throw new ApiRequestException("exception.userNotAdd");
        }
    }

    @Override
    public User updateUserById(User updateUser, long id) {
        if (updateUser.getPassword() != null && updateUser.getPassword().length() < 30) {
            updateUser.setPassword(bCryptPasswordEncoder.encode(updateUser.getPassword()));
        }
        userRepository.save(updateUser);
        return updateUser;
    }

    public User updateUserById(UserRequest userRequest, long id) {
        User updateUser = userMapper.updateUserFromUserRequest(userRequest, getUserById(id));
        return updateUserById(updateUser, id);
    }

    @Override
    public void deleteUserById(long id, Set<Role> roles) {
        boolean isEmployee = Utils.isEmployee(roles);
        if (isEmployee) {
            userRepository.deleteById(id);
        } else {
            throw new ApiRequestException("exception.userNotDelete");
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

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private void checkUsername(String username) {
        boolean checkUser = existsUserByUserName(username);
        if (checkUser) {
            throw new ResourceAlreadyExists("userName", "exception.usernameExists");
        }
    }

    private void checkEmail(String email) {
        boolean checkEmail = existsByEmail(email);
        if (checkEmail) {
            throw new ResourceAlreadyExists("email", "exception.emailExists");
        }
    }


    private void preparingToAddUser(User newUser) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setEnabled(false);
        Role userRole = roleService.findByName("ROLE_USER");
        newUser.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        NumberAuthorization numberAuthorization = new NumberAuthorization();
        numberAuthorization.setQuantity(3);
        numberAuthorization.setLastAuthorizationDate(LocalDateTime.now());
        newUser.setNumberAuthorization(numberAuthorization);
    }
}
