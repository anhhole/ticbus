package com.ticbus.backend.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AnhLH
 */
@Data
@Getter
@Setter
public class OTPCreateResponse {

  private String status;
  private String code;
  private String message;
  private OTPCreateDataResponse data;
}
