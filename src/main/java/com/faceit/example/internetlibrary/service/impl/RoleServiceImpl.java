package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.exception.ResourceNotFoundException;
import com.faceit.example.internetlibrary.util.Utils;
import com.faceit.example.internetlibrary.model.Role;
import com.faceit.example.internetlibrary.repository.RoleRepository;
import com.faceit.example.internetlibrary.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        return Utils.getDataFromTypeOptional(optionalRole);
    }

    @Override
    public Role addRole(Role newRole) {
        return roleRepository.save(newRole);
    }

    @Override
    public Role updateRoleById(Role updateRole, long id) {
        Role role = getRoleById(id);
        if (role != null) {
            updateRole.setId(id);
        } else {
            throw new ResourceNotFoundException("Not found");
        }
        roleRepository.save(updateRole);
        return updateRole;
    }

    @Override
    public void deleteRoleById(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role findByName(String role) {
        return roleRepository.findByName(role);
    }
}
