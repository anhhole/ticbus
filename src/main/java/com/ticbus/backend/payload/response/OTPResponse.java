package com.ticbus.backend.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OTPResponse {

  private String status;
  private OTPResponseData data;
}
