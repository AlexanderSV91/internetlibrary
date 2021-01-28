package com.faceit.example.internetlibrary.service.mysql;

import com.faceit.example.internetlibrary.dto.request.mysql.RoleRequest;
import com.faceit.example.internetlibrary.model.mysql.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRole();

    Role getRoleById(long id);

    Role addRole(Role newRole);

    Role updateRoleById(RoleRequest roleRequest, long id);

    void deleteRoleById(long id);

    Role findByName(String role);
}
