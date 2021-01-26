package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.dto.request.RoleRequest;
import com.faceit.example.internetlibrary.dto.response.RoleResponse;
import com.faceit.example.internetlibrary.exception.ResourceNotFoundException;
import com.faceit.example.internetlibrary.mapper.RoleMapper;
import com.faceit.example.internetlibrary.model.Role;
import com.faceit.example.internetlibrary.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
@Tag(name = "Roles", description = "interaction with roles")
@SecurityRequirement(name = "basic")
public class RoleControllerRest {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleControllerRest(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @GetMapping("/role")
    @Operation(summary = "get all roles", description = "allows you to get all roles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class)))),
            @ApiResponse(responseCode = "404", description = "roles not found")})
    public List<RoleResponse> getAllRole() {
        List<Role> roles = roleService.getAllRole();
        if (roles != null) {
            return roleMapper.rolesToRolesResponse(roles);
        }
        throw new ResourceNotFoundException("exception.notFound");
    }

    @GetMapping("/role/{id}")
    @Operation(summary = "get a certain role", description = "allows you to get a certain role")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = RoleResponse.class))),
            @ApiResponse(responseCode = "404", description = "role not found")})
    public RoleResponse getAllRoleById(@Parameter(description = "Role id") @PathVariable long id) {
        return roleMapper.roleToRoleResponse(roleService.getRoleById(id));
    }

    @PostMapping("/role")
    @Operation(summary = "add new role", description = "allows you to add new role")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "role created",
            content = @Content(schema = @Schema(implementation = RoleResponse.class))),
            @ApiResponse(responseCode = "400", description = "role not add")})
    public RoleResponse addRole(@Valid @RequestBody RoleRequest roleRequest) {
        Role role = roleMapper.roleRequestToRole(roleRequest);
        return roleMapper.roleToRoleResponse(roleService.addRole(role));
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
            content = @Content(schema = @Schema(implementation = RoleResponse.class))),
            @ApiResponse(responseCode = "404", description = "role not found")})
    public RoleResponse updateRoleById(@RequestBody RoleRequest roleRequest,
                               @Parameter(description = "Role id") @PathVariable Long id) {
        Role role = roleMapper.roleRequestToRole(roleRequest);
        return roleMapper.roleToRoleResponse(roleService.updateRoleById(role, id));
    }
}
