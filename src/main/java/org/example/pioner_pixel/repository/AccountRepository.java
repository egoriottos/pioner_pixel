package org.example.pioner_pixel.repository;

import jakarta.persistence.LockModeType;
import io.lettuce.core.dynamic.annotation.Param;
import org.example.pioner_pixel.domain.entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.user.id = :userId")
    Optional<Account> findByUserIdWithLock(@Param("userId") Long userId);

    @Query("SELECT a FROM Account a")
    @EntityGraph(attributePaths = {}, type = EntityGraph.EntityGraphType.LOAD)
    List<Account> findAllWithoutUser();
}
