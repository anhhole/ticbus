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
public class CreateOTPRequest {

  private String to;
  private String content;
  private String app_id;
}
