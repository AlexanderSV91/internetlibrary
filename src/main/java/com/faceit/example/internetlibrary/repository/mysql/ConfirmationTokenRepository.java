package com.faceit.example.internetlibrary.repository.mysql;

import com.faceit.example.internetlibrary.model.mysql.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    ConfirmationToken findByToken(String token);

    boolean existsByToken(String token);
}
