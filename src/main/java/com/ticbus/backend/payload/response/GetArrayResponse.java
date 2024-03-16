package com.ticbus.backend.payload.response;


import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetArrayResponse<T> extends BaseResponse {

  private long total;
  private List<T> rows;

  public GetArrayResponse() {
    super();
    this.total = 0;
    this.rows = new ArrayList<>();
  }

  public GetArrayResponse(long total, List<T> rows, String message, int code) {
    super(code, message);
    this.total = total;
    this.rows = rows;
  }

  public void setSuccess(List<T> rows, long total) {
    super.setSuccess();
    this.rows = rows;
    this.total = total;
  }

  @Override
  public String toString() {
    return "GetArrayResponse{" +
        "total=" + total +
        ", rows=" + rows +
        ", rc=" + rc +
        ", rd='" + rd + '\'' +
        '}';
  }
}
