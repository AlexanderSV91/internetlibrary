package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.Role;
import com.faceit.example.internetlibrary.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
@Tag(name = "Roles", description = "interaction with roles")
public class RoleControllerRest {
    private final RoleService roleService;

    @Autowired
    public RoleControllerRest(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/role")
    @Operation(summary = "get all roles", description = "allows you to get all roles")
    public List<Role> getAllRole() {
        return roleService.getAllRole();
    }

    @GetMapping("/role/{id}")
    @Operation(summary = "get a certain role", description = "allows you to get a certain role")
    public Role getAllRoleById(@Parameter(description = "Role id") @PathVariable long id) {
        return roleService.getRoleById(id);
    }

    @PostMapping("/role")
    @Operation(summary = "add new role", description = "allows you to add new role")
    public Role addRole(@RequestBody Role newRole) {
        return roleService.addRole(newRole);
    }

    @DeleteMapping("/role/{id}")
    @Operation(summary = "delete a certain role", description = "allows you to delete a certain role")
    public void deleteRoleById(@Parameter(description = "Role id") @PathVariable long id) {
        roleService.deleteRoleById(id);
    }

    @PutMapping("/role/{id}")
    @Operation(summary = "update a certain role", description = "allows you to update a certain role")
    public Role updateRoleById(@RequestBody Role updateRole,
                               @Parameter(description = "Role id") @PathVariable Long id) {
        return roleService.updateRoleById(updateRole, id);
    }
}
