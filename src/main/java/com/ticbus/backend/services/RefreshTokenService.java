package com.ticbus.backend.services;

import com.ticbus.backend.exception.EntityType;
import com.ticbus.backend.exception.ExceptionType;
import com.ticbus.backend.exception.TicbusException;
import com.ticbus.backend.model.Employee;
import com.ticbus.backend.model.RefreshToken;
import com.ticbus.backend.repository.RefreshTokenRepository;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AnhLH
 */
@Service
@Transactional
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.token.refresh.duration}")
    private Long refreshTokenDurationMs;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    /**
     * Find a refresh token based on the natural id i.e the token itself
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Persist the updated refreshToken instance to database
     */
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByEmployeeId(int id) {
      return refreshTokenRepository.findByEmployeeId(id)
              .orElseThrow(() ->
                      exception(EntityType.REFRESH_TOKEN, ExceptionType.ENTITY_EXCEPTION, String.valueOf(id)));
    }
    /**
     * Creates and returns a new refresh token
     */
    public RefreshToken createRefreshToken(Employee employee) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Date.from(Instant.now().plusMillis(refreshTokenDurationMs)));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setRefreshCount(0L);
        refreshToken.setEmployee(employee);
        return refreshToken;
    }

    /**
     * Verify whether the token provided has expired or not on the basis of the current
     * server time and/or throw error otherwise
     */
    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Date.from(Instant.now())) < 0) {
            throw exception(EntityType.REFRESH_TOKEN, ExceptionType.ENTITY_EXCEPTION, String.valueOf(token.getToken()));
        }
    }

    /**
     * Delete the refresh token associated with the user device
     */
    public void deleteById(Integer id) {
        refreshTokenRepository.deleteById(id);
    }

    public void deleteByEmployeeId(Integer id){
        refreshTokenRepository.deleteByEmployeeId(id);
    }

    /**
     * Increase the count of the token usage in the database. Useful for
     * audit purposes
     */
    public void increaseCount(RefreshToken refreshToken) {
        refreshToken.incrementRefreshCount();
        save(refreshToken);
    }

    /**
     * Returns a new RuntimeException
     */
    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType,
                                       String... args) {
        return TicbusException.throwException(entityType, exceptionType, args);
    }
}
