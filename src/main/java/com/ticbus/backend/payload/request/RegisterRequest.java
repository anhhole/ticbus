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
public class RegisterRequest {

  @NotBlank
  private String phone;
  @NotBlank
  private String name;
  private String mail;
  @NotBlank
  private String address;
  @NotBlank
  private String password;

}
