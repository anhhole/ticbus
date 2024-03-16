package com.ticbus.backend.payload.response;


import com.ticbus.backend.common.ErrorCodeDefs;

/**
 * @author AnhLH
 */
public class BaseResponse {

  protected int rc;
  protected String rd;
  protected String rk;

  public BaseResponse(int rc, String rd) {
    this.rc = rc;
    this.rd = rd;
  }

  public BaseResponse() {
    rc = ErrorCodeDefs.UNKNOWN;
    rd = "UNKNOWN";
  }

  public int getRc() {
    return rc;
  }

  public void setRc(int rc) {
    this.rc = rc;
  }

  public String getRd() {
    return rd;
  }

  public void setRd(String rd) {
    this.rd = rd;
  }

  public void setSuccess() {
    this.rc = ErrorCodeDefs.ERR_CODE_OK;
    this.rd = "OK";
  }

  public void setFailed(String msg) {
    this.rc = ErrorCodeDefs.ERR_CODE_FAILED;
    this.rd = msg;
  }

  public void setPhoneInvalid() {
    this.rc = ErrorCodeDefs.ERR_CODE_PHONE_INVALID;
    this.rd = "Số điện thoại không đúng";
  }

  public void setPhoneDup() {
    this.rc = ErrorCodeDefs.ERR_CODE_PHONE_NUMBER_USED;
    this.rd = "Số điện thoại đã đuợc sử dụng";
  }

  public void setVerifyFailed() {
    this.rc = ErrorCodeDefs.ERR_CODE_VERIFY_FAILED;
    this.rd = "Mã Không đúng hoặc hết hạn vui lòng thử lại";
  }

  public void setFailed(int code, String msg) {
    this.rc = code;
    this.rd = msg;
  }

  public void setFailed() {
    this.setFailed("Action failed !");
  }

  public void setUnAuthor() {
    this.rc = ErrorCodeDefs.UNAUTHORIZED;
    this.rd = "UNAUTHORIZED";
  }

  public void setDataExist() {
    this.rc = ErrorCodeDefs.ERR_CODE_DATA_EXIST;
    this.rd = "du lieu da ton tai";
  }

  public void setUserNotEnable() {
    this.rc = ErrorCodeDefs.ERR_CODE_USER_NOT_ENABLE;
    this.rd = "Tài khoản của bạn không ";
  }

  public void setParamsInvalid() {
    this.setFailed(ErrorCodeDefs.ERR_CODE_PARAMS_INVALID, "Params invalid! Or Not created");
  }

  public void setServerError() {
    this.setFailed(ErrorCodeDefs.ERR_CODE_SERVER_ERROR, "Server error!");
  }

  public void setItemNotFound(String msg) {
    this.rc = ErrorCodeDefs.ERR_CODE_ITEM_NOT_FOUND;
    this.rd = msg;
  }

  public void setItemNotFound() {
    this.setItemNotFound("items not found");
  }

  @Override
  public String toString() {
    return "{" +
        "rc=" + rc +
        ", rd='" + rd + '\'' +
        '}';
  }
}
