package com.ticbus.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author AnhLH
 */
@Component
public class JwtTokenProvider {

  private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

  @Value("${app.jwtSecret}")
  private String jwtSecret;

  @Value("${app.jwtExpirationInMs}")
  private long jwtExpirationInMs;

  public String generateToken(Authentication authentication) {

    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    Date now = new Date();
    // not set
    Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

    return Jwts.builder()
        .setSubject(String.valueOf(userPrincipal.getPhone()))
        .setId(String.valueOf(userPrincipal.getId()))
        .setIssuedAt(new Date())
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String generateToken(String phone, Integer id) {
    return Jwts.builder()
        .setSubject(String.valueOf(phone))
        .setId(String.valueOf(id))
        .setIssuedAt(new Date())
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String generateCMSToken(Authentication authentication) {
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
    return Jwts.builder()
        .setSubject(String.valueOf(userPrincipal.getPhone()))
        .setId(String.valueOf(userPrincipal.getId()))
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getPhoneFromJWT(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody();
    return claims.getSubject();
  }

  public Date getExDate(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody();
    return claims.getExpiration();
  }

  public Integer getIdFromToken(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody();
    return Integer.parseInt(claims.getId());
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException ex) {
      logger.error("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      logger.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      logger.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      logger.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      logger.error("JWT claims string is empty.");
    }
    return false;
  }

  public Long getExpiryDuration() {
    return jwtExpirationInMs;
  }
}
