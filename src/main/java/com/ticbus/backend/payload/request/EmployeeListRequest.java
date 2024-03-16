package com.ticbus.backend.payload.request;


import lombok.Getter;
import lombok.Setter;

/**
 * @author AnhLH
 */

@Getter
@Setter
public class EmployeeListRequest extends PageRequest {

  private String name;
  private String phone;
  private String address;
  private String email;
  private Integer status;
}
