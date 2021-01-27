package com.faceit.example.internetlibrary.mapper;

import com.faceit.example.internetlibrary.dto.request.UserRequest;
import com.faceit.example.internetlibrary.dto.response.UserResponse;
import com.faceit.example.internetlibrary.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse userToUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    User userRequestToUser(UserRequest userRequest);

    List<UserResponse> usersToUsersResponse(List<User> users);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "id", ignore = true)
    User updateUserFromUserRequest(UserRequest userRequest, @MappingTarget User user);
}
