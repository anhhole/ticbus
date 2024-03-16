package com.ticbus.backend.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author AnhLH
 */
@Data
@Setter
@Getter
@NoArgsConstructor
public class PageRequest {

  private Integer page = 0;
  private Integer size = 20;
  private String sort = "createdTime,asc";
  private String search;
}
