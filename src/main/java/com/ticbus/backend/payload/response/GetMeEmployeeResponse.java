package com.ticbus.backend.payload.response;

import com.ticbus.backend.common.enumeration.EnumRole;
import java.util.Date;
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
public class GetMeEmployeeResponse {

  private Integer id;
  private String name;
  private String phone;
  private String lastIp;
  private String mail;
  private Date timeExpired;
  private EnumRole role;

}
