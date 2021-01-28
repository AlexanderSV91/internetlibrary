package com.faceit.example.internetlibrary.repository.mysql;

import com.faceit.example.internetlibrary.model.mysql.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String role);
}
