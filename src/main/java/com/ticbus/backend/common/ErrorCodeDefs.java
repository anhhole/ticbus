package com.ticbus.backend.common;

/**
 * @author AnhLH
 */
public class ErrorCodeDefs {

  public static final int UNKNOWN = -10;
  public static final int INVALID_JWT_SIGNATURE = -100;
  public static final int INVALID_JWT_TOKEN = -101;
  public static final int UNSUPPORTED_JWT_TOKEN = -102;
  public static final int JWT_CLAIMS_EMPTY = -103;
  public static final int UNAUTHORIZED = 401;
  public static final String SECRET = "";
  public static final long TIME_TOKEN_EXPIRE = 5184000000L; //60 day
  public final static int ERR_CODE_OTHER = -7;
  public final static int ERR_CODE_SERVER_ERROR = -1;
  public static final int ERR_CODE_OK = 0;
  public static final int ERR_CODE_FAILED = 1;
  public static final int ERR_CODE_ITEM_NOT_FOUND = 2;
  public static final int ERR_CODE_USER_NOT_FOUND = 3;
  public static final int ERR_CODE_CHANNEL_NOT_FOUND = 4;
  public final static int ERR_CODE_PARAMS_INVALID = 5;
  public final static int ERR_CODE_EXPERTISE_FAILED = 6;
  public final static int ERR_CODE_NOT_COMPLETE_IDENTITY = 7;
  public final static int ERR_CODE_NOT_COMPLETE_REFERENCE = 8;
  public final static int ERR_CODE_NOT_COMPLETE_BANKINFO = 9;
  public final static int ERR_CODE_NOT_COMPLETE = 10;
  public final static int ERR_CODE_NOT_LOAN_TO = 11;
  public final static int ERR_CODE_PHONE_INVALID = 12;
  public final static int ERR_CODE_VERIFY_FAILED = 13;
  public final static int ERR_CODE_PHONE_NUMBER_USED = 14;
  public final static int ERR_CODE_USER_NOT_ENABLE = 15;
  public final static int ERR_CODE_DATA_EXIST = 16;
  public final static int ERR_CODE_PASS_LEGHT_INVALID = 17;
  public final static int ERR_CODE_USER_NOT_CONFIRMED = 18;
  public final static int ERR_CODE_TOKEN_TIME_OUT = 19;
  public final static int ERR_CODE_ID_EXIST = 21;
  public final static int ERR_CODE_USER_EXIST = 22;
  public final static int ERR_CODE_SOURCE_BLOCK = 23;
  public final static int ERR_CODE_EMAIL_USED = 24;
  public final static int ERR_CODE_TRANSACTION_PROCESSING = 25;
  public final static int ERR_CODE_PASSWORD_WRONG_THAN_MAX = 26;
  public final static int ERR_CODE_PASSWORD_WRONG = 27;
  public final static int ERR_CODE_SOURCE_YOURS = 28;
  public final static int ERR_CODE_PASSWORD_SIMILAR = 29;


}
