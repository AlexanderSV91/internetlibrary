package com.faceit.example.internetlibrary.mapper;

import com.faceit.example.internetlibrary.dto.request.RoleRequest;
import com.faceit.example.internetlibrary.dto.response.RoleResponse;
import com.faceit.example.internetlibrary.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponse roleToRoleResponse(Role role);

    Role roleRequestToRole(RoleRequest roleRequest);

    List<RoleResponse> rolesToRolesResponse(List<Role> roles);
}
