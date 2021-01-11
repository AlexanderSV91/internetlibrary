package com.faceit.example.internetlibrary.repository;

import com.faceit.example.internetlibrary.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
