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
public class OTPCreateDataResponse {

  public Integer pin_code;
  public Long tranId;
  public Integer totalSMS;
  public Integer totalPrice;
}
