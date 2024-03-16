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
public class SearchRequest {

  private String search;
}
