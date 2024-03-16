package com.ticbus.backend.exception;

/**
 * @author AnhLH
 */
public enum ExceptionType {
  ENTITY_NOT_FOUND("not.found"),
  DUPLICATE_ENTITY("duplicate"),
  ENTITY_EXCEPTION("exception"),
  OVERLAP_TIME_EXCEPTION("overlap.time.exception");

  String value;

  ExceptionType(String value) {
    this.value = value;
  }

  String getValue() {
    return this.value;
  }
}
