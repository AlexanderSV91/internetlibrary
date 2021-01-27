package com.faceit.example.internetlibrary.service;

import com.faceit.example.internetlibrary.dto.request.RoleRequest;
import com.faceit.example.internetlibrary.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRole();

    Role getRoleById(long id);

    Role addRole(Role newRole);

    Role updateRoleById(RoleRequest roleRequest, long id);

    void deleteRoleById(long id);

    Role findByName(String role);
}
