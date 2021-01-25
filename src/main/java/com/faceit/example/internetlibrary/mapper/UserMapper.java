package com.faceit.example.internetlibrary.mapper;

import com.faceit.example.internetlibrary.dto.request.UserRequest;
import com.faceit.example.internetlibrary.dto.response.UserResponse;
import com.faceit.example.internetlibrary.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse userToUserResponse(User user);

    User userRequestToUser(UserRequest userRequest);
}
