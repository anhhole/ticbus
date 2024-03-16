package com.ticbus.backend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author AnhLH
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtAuthenticationMobileResponse {

  private String accessToken;

}
