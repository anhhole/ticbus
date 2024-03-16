package com.ticbus.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author AnhLH
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_customer")
public class Customer extends BaseModel implements Serializable {

  private static final long serialVersionUID = 7156526077883281620L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String phone;
  private String mail;
  private String address;
  @JsonIgnore
  private String password;
  private String avatar;
  @Column(name = "last_login_time")
  private Date lastLoginTime;
  @Column(name = "last_ip")
  private String lastIP;
  private Integer status;
  @Column(name = "is_active")
  private Boolean isActive = false;
}
