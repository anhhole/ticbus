package com.ticbus.backend.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetSingleItemResponse<T> extends BaseResponse {

  private T item;

  public GetSingleItemResponse() {
  }

  public GetSingleItemResponse(T item) {
    this.item = item;
  }

  public GetSingleItemResponse(T item, String message, int code) {
    super(code, message);
    this.item = item;
  }

  public void setSuccess(T item) {
    super.setSuccess();
    this.item = item;
  }

}