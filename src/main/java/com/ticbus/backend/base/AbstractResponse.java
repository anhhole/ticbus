package com.ticbus.backend.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class AbstractResponse<ID, T> {

  Boolean success = Boolean.FALSE;
  ID objectId;
  T object;
  List<T> objects;
  Long totalResultCount;
  String message;
}
