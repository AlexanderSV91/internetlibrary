package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.configuration.MyUserDetails;
import com.faceit.example.internetlibrary.dto.request.mysql.UserRequest;
import com.faceit.example.internetlibrary.dto.response.mysql.UserResponse;
import com.faceit.example.internetlibrary.model.mysql.NumberAuthorization;
import com.faceit.example.internetlibrary.model.mysql.Role;
import com.faceit.example.internetlibrary.model.mysql.User;
import com.faceit.example.internetlibrary.service.mysql.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("GET /api/user success")
    @WithMockUser(username = "1", password = "1", roles = "EMPLOYEE")
    void getAllUserByUsername() throws Exception {
        User userAuth = getUserAuth();
        User user1 = new User();
        user1.setId(1);
        user1.setUserName("123456");
        user1.setPassword("123456");
        user1.setFirstName("1");
        user1.setLastName("1");
        user1.setEmail("1@1.com");
        user1.setAge(1);

        User user2 = new User();
        user2.setId(2);
        user2.setUserName("1234567");
        user2.setPassword("123456");
        user2.setFirstName("2");
        user2.setLastName("2");
        user2.setEmail("2@2.com");
        user2.setAge(2);
        List<User> users = Lists.newArrayList(user1, user2);

        UserResponse userResponse1 = new UserResponse();
        userResponse1.setId(1);
        userResponse1.setUserName("123456");
        userResponse1.setFirstName("1");
        userResponse1.setLastName("1");
        userResponse1.setEmail("1@1.com");
        userResponse1.setAge(1);

        UserResponse userResponse2 = new UserResponse();
        userResponse2.setId(2);
        userResponse2.setUserName("1234567");
        userResponse2.setFirstName("2");
        userResponse2.setLastName("2");
        userResponse2.setEmail("2@2.com");
        userResponse2.setAge(2);
        List<UserResponse> userResponses = Lists.newArrayList(userResponse1, userResponse2);

        doReturn(users).when(userService).getAllUserByUsername(userAuth);

        mockMvc.perform(get("/api/user")
                .with(user(new MyUserDetails(userAuth)))
                .content(asJsonString(users))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(userResponses)));
    }

    @Test
    @DisplayName("GET /api/current-user success")
    @WithMockUser(username = "1", password = "1", roles = "EMPLOYEE")
    void findUserByUserName() throws Exception {
        User userAuth = getUserAuth();
        UserResponse userResponse = getUserResponse();
        User userReturn = getUser();
        userReturn.setId(1);

        doReturn(userReturn).when(userService).findUserByUserName("123456");

        mockMvc.perform(get("/api/current-user")
                .with(user(new MyUserDetails(userAuth)))
                .content(asJsonString(userReturn))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(userResponse)));
    }

    @Test
    @DisplayName("GET /api/user/{id} success")
    @WithMockUser(username = "1", password = "1", roles = "EMPLOYEE")
    void getUserById() throws Exception {
        User user = getUserAuth();
        UserResponse userResponse = getUserResponse();

        when(userService.getUserById(1)).thenReturn(user);
        mockMvc.perform(get("/api/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(userResponse)));
    }

    @Test
    @DisplayName("POST /api/user success")
    @WithMockUser(value = "1", username = "1", password = "1", roles = "EMPLOYEE")
    void addUser() throws Exception {
        User userAuth = getUserAuth();
        UserResponse userResponse = getUserResponse();
        User userPost = getUser();
        User userReturn = getUser();
        userReturn.setId(1);

        doReturn(userReturn).when(userService).addUser(userPost, userAuth.getRoles());

        mockMvc.perform(post("/api/user")
                .with(user(new MyUserDetails(userAuth)))
                .content(asJsonString(userPost))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(userResponse)));
    }

    @Test
    @DisplayName("PUT /api/user/{id} success")
    @WithMockUser(value = "1", username = "1", password = "1", roles = "EMPLOYEE")
    void updateUserById() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("123456");
        userRequest.setFirstName("123456");
        userRequest.setLastName("123456");
        userRequest.setEmail("1@1.com");
        userRequest.setAge(1);
        UserResponse userResponse = getUserResponse();
        User userAuth = getUserAuth();
        User userReturn = getUser();
        userReturn.setId(1);

        doReturn(userReturn).when(userService).updateUserById(userRequest, 1L);

        mockMvc.perform(put("/api/user/{id}", 1L)
                .with(user(new MyUserDetails(userAuth)))
                .content(asJsonString(userRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(userResponse)));
    }

    private static UserResponse getUserResponse() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1);
        userResponse.setUserName("123456");
        userResponse.setFirstName("123456");
        userResponse.setLastName("123456");
        userResponse.setEmail("1@1.com");
        userResponse.setAge(1);
        return userResponse;
    }

    private static User getUser() {
        User user = new User();
        user.setUserName("123456");
        user.setPassword("123456");
        user.setFirstName("123456");
        user.setLastName("123456");
        user.setEmail("1@1.com");
        user.setAge(1);
        return user;
    }

    private static User getUserAuth() {
        NumberAuthorization numberAuthorization = new NumberAuthorization(1, 3, LocalDateTime.now());
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1, "ROLE_EMPLOYEE"));
        return new User(1, "123456", "123456", "123456", "123456", "1@1.com", 1, true, roleSet, numberAuthorization);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}