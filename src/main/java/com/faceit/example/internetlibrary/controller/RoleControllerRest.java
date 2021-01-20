package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.Role;
import com.faceit.example.internetlibrary.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Role.class))))})
    public List<Role> getAllRole() {
        return roleService.getAllRole();
    }

    @GetMapping("/role/{id}")
    @Operation(summary = "get a certain role", description = "allows you to get a certain role")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Role.class)))),
            @ApiResponse(responseCode = "404", description = "role not found")})
    public Role getAllRoleById(@Parameter(description = "Role id") @PathVariable long id) {
        return roleService.getRoleById(id);
    }

    @PostMapping("/role")
    @Operation(summary = "add new role", description = "allows you to add new role")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "role created",
            content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "role not add")})
    public Role addRole(@RequestBody Role newRole) {
        return roleService.addRole(newRole);
    }

    @DeleteMapping("/role/{id}")
    @Operation(summary = "delete a certain role", description = "allows you to delete a certain role")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "role not delete")})
    public void deleteRoleById(@Parameter(description = "Role id") @PathVariable long id) {
        roleService.deleteRoleById(id);
    }

    @PutMapping("/role/{id}")
    @Operation(summary = "update a certain role", description = "allows you to update a certain role")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "404", description = "role not found")})
    public Role updateRoleById(@RequestBody Role updateRole,
                               @Parameter(description = "Role id") @PathVariable Long id) {
        return roleService.updateRoleById(updateRole, id);
    }
}
