package com.ticbus.backend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author AnhLH
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class GetMeCustomerResponse {

  private Integer id;
  private String name;
  private String phone;
  private String avatar;


}
