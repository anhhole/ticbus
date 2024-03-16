package com.ticbus.backend.util;

public class CommonConstants {

    /**
     * The FAILED status of the response object
     */
    public static final String STR_FAIL_STATUS                          = "FAILED";
    /**
     * The SUCCESS status of the response object
     */
    public static final String STR_SUCCESS_STATUS                       = "SUCCESS";

    /**
     * Bus Error Message
     */
    public static final String CREATE_NEW_BUS_FAIL                      = "Lỗi trong quá trình tạo mới thông tin xe.";

    public static final String BUS_IS_EXISTED                           = "Thông tin biển sổ này đã có trong hệ thống.";

    public static final String UPDATE_BUS_FAIL                          = "Lỗi trong quá trình cập nhật thông tin xe.";

    public static final String BUS_IS_NOT_EXISTED                       = "Thông tin biển số này không có trong hệ thống";

    /**
     * System Error Messages
     */
    public static final String SETTING_SYSTEM_INFORMATION_FAIL          = "Lỗi trong quá trình thiết lập thông tin hệ thống.";

    public static final String UPDATING_SYSTEM_INFORMATION_FAIL         = "Lỗi trong quá trình cập nhật thông tin hệ thống";

    /**
     * Employee Error Messages
     */
    public static final String CREATE_NEW_EMPLOYEE_FAIL                 = "Lỗi trong quá trình tạo mới thông tin nhân viên";

    public static final String UPDATE_EMPLOYEE_FAIL                     = "Lỗi trong quá trình cập nhật thông tin nhân viên";

    public static final String EMPLOYEE_DOESNT_EXISTED                  = "Không có thông tin nhân viên trong hệ thống.";

    public static final String THIS_PHONE_IS_USED_IN_ANOTHER_EMPLOYEE   = "Số điện thoại này đã được đăng ký với tài khoản nhân viên khác. Không thể cập nhật thông tin này.";

    public static final String SYSTEM_DOESNT_EXIST_EMPLOYEES            = "Hệ thống không tồn tại nhân viên.";

    /**
     * Trip Error Message
     */
    public static final String CREATE_NEW_TRIP_FAIL                     = "Lỗi trong quá trình tạo mới thông tin chuyến đi";

    public static final String TRIP_IS_EXISTED                          = "Thông tin chuyến đi đã tồn tại trong hệ thống.";

    public static final String GET_ALL_TRIP_INFORMATION_FAIL            = "Lỗi trong quá trình lấy thông tin chuyến đi";

    public static final String UPDATE_TRIP_FAIL                         = "Lỗi trong quá trình cập nhật thông tin chuyến đi";

    public static final String TRIP_IS_NOT_EXISTED                      = "Không có thông tin chuyến đi trong hệ thống.";

    public static final String SEARCH_TRIP_FAIL                         = "Lỗi trong quá trình tìm kiếm thông tin chuyến đi";

    public static final String UPDATE_TRIP_ERROR_EXISTED                = "Không thể cập nhật thông tin này. Chuyến đi đã tồn tại trong hệ thống.";
}
