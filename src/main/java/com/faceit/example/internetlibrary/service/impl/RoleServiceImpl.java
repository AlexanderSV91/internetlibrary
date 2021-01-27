package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.dto.request.RoleRequest;
import com.faceit.example.internetlibrary.mapper.RoleMapper;
import com.faceit.example.internetlibrary.model.Role;
import com.faceit.example.internetlibrary.repository.RoleRepository;
import com.faceit.example.internetlibrary.service.RoleService;
import com.faceit.example.internetlibrary.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
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
    public Role updateRoleById(RoleRequest roleRequest, long id) {
        Role updateRole = roleMapper.updateRoleFromRoleRequest(roleRequest, getRoleById(id));
        return roleRepository.save(updateRole);
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
