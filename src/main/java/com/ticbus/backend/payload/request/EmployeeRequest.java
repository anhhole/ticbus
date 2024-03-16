package com.ticbus.backend.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author AnhLH
 */
@Data
@Getter
@Setter
@Accessors(chain = true)
public class EmployeeRequest {

  @NotNull
  @NotBlank
  private String name;
  @NotNull
  private String phone;
  @NotNull
  private String address;
  @NotNull
  private String password;
  private String email;
}
