package com.ticbus.backend.model.specification;

import org.springframework.data.jpa.domain.Specification;

/**
 * @author AnhLH
 */
public abstract class BaseSpecification<T, U> {

  private final String wildcard = "%";

  public abstract Specification<T> getFilter(U request);

  protected String containsLowerCase(String searchField) {
    return wildcard + searchField.toLowerCase() + wildcard;
  }

  protected Integer containsNumberType(Integer searchField) {
    return searchField;
  }

}
