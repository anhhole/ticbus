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
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TermVector;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_departure")
public class Departure implements Serializable {
  private static final long serialVersionUID = 7156526077883281632L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String address;
  private Integer status;
  @ManyToOne(cascade = CascadeType.ALL, targetEntity = City.class)
  @JoinColumn(name = "city_id")
  private City city;
  @Column(name = "created_time")
  private Date createdTime;
  @Column(name = "last_modified_time")
  private Date lastModifiedDate;
}
