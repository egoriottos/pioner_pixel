package org.example.pioner_pixel.repository;

import org.example.pioner_pixel.domain.entity.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneData,Long> {
    Optional<PhoneData> findByPhone(String phone);
}
