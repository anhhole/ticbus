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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_destination")
public class Destination implements Serializable {

  private static final long serialVersionUID = 7156526077883281622L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String address;
  @ManyToOne(cascade = CascadeType.ALL, targetEntity = City.class)
  @JoinColumn(name = "city_id")
  private City city;
  private Integer status;
  @Column(name = "created_time")
  private Date createdTime;
  @Column(name = "last_modified_time")
  private Date lastModifiedDate;
}
