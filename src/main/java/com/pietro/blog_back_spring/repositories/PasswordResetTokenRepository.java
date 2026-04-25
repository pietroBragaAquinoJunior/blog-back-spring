package com.pietro.blog_back_spring.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pietro.blog_back_spring.entities.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByUuidToken(String token);
}
