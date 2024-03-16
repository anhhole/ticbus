package com.ticbus.backend.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

/**
 * @author AnhLH
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "tb_refresh_token")
public class RefreshToken extends BaseModel implements Serializable {

  private static final long serialVersionUID = 7156526077883281634L;
  @Id
  @Column(name = "token_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "token", nullable = false, unique = true)
  @NaturalId(mutable = true)
  private String token;

  @OneToOne(optional = false, cascade = CascadeType.MERGE, targetEntity = Employee.class)
  @JoinColumn(name = "employee_id", unique = true)
  private Employee employee;

  @Column(name = "REFRESH_COUNT")
  private Long refreshCount;

  @Column(name = "expiry_date", nullable = false)
  private Date expiryDate;

  public void incrementRefreshCount() {
    refreshCount = refreshCount + 1;
  }

}
