package com.ticbus.backend.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ForgotPassRequest {

  private String phone;
  private String newPass;
}
