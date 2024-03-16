package com.ticbus.backend.payload.request;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerListRequest extends PageRequest implements Serializable {

  private String name;
  private String phone;
  private String mail;
  private String address;
}
