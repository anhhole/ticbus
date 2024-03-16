package com.ticbus.backend.repository;

import com.ticbus.backend.model.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author AnhLH
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

  Optional<RefreshToken> findByToken(String token);

  Optional<RefreshToken> findByEmployeeId(Integer id);

  void deleteByEmployeeId(Integer id);
}
