package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.Role;
import com.faceit.example.internetlibrary.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class RoleControllerRest {
    private final RoleService roleService;

    @Autowired
    public RoleControllerRest(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/role")
    public List<Role> getAllRole() {
        return roleService.getAllRole();
    }

    @GetMapping("/role/{id}")
    public Role getAllRoleById(@PathVariable long id) {
        return roleService.getRoleById(id);
    }

    @PostMapping("/role")
    public Role addRole(@RequestBody Role newRole) {
        return roleService.addRole(newRole);
    }

    @DeleteMapping("/role/{id}")
    public void deleteRoleById(@PathVariable long id) {
        roleService.deleteRoleById(id);
    }

    @PutMapping("/role/{id}")
    public Role updateRoleById(@RequestBody Role updateRole, @PathVariable Long id) {
        return roleService.updateRoleById(updateRole, id);
    }
}
