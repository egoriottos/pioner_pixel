package org.example.pioner_pixel.repository;

import org.example.pioner_pixel.domain.entity.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailData,Long> {
    Optional<EmailData> findByEmail(String email);
}
