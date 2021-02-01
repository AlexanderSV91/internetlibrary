package com.faceit.example.internetlibrary.repository.mysql;

import com.faceit.example.internetlibrary.model.mysql.NumberAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberAuthorizationRepository extends JpaRepository<NumberAuthorization, Long> {
}
