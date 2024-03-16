package com.ticbus.backend.payload.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AnhLH
 */
@Data
@Getter
@Setter
public class TokenRefreshRequest {

  @NotBlank
  private String refreshToken;
}
