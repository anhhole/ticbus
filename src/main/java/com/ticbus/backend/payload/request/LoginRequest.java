package com.ticbus.backend.payload.request;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AnhLH
 */
@Data
@Getter
@Setter
public class LoginRequest {

  @NotNull
  private String phone;
  @NotNull
  private String password;
}
