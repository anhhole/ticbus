package com.ticbus.backend.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AnhLH
 */
@Data
@Getter
@Setter
public class OTPRequest {

  private String phone;
  private String otp;
}
