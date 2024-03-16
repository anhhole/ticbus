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
public class VerifyOTPRequest {

  private String phone;
  private String pin_code;
  private String app_id;
}
