package com.faceit.example.internetlibrary.mapper.mysql;

import com.faceit.example.internetlibrary.dto.request.mysql.RoleRequest;
import com.faceit.example.internetlibrary.dto.response.mysql.RoleResponse;
import com.faceit.example.internetlibrary.model.mysql.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponse roleToRoleResponse(Role role);

    @Mapping(target = "id", ignore = true)
    Role roleRequestToRole(RoleRequest roleRequest);

    List<RoleResponse> rolesToRolesResponse(List<Role> roles);

    @Mapping(target = "id", ignore = true)
    Role updateRoleFromRoleRequest(RoleRequest roleRequest, @MappingTarget Role role);
}
