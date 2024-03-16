package com.ticbus.backend.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OTPResponseData {

  private String pin;
  private String phone;
  private Boolean verified;
  private Integer remainingAttempts;
}
