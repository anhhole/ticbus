package com.ticbus.backend.common.enumeration;

/**
 * @author AnhLH
 */
public enum DeviceType {
  IOS(3, "IOS"),
  WEB(2, "WEB"),
  ANDROID(1, "ANDROID");

  private int id;
  private String roleName;

  DeviceType(int id, String name) {
    this.id = id;
    this.roleName = name;
  }

  public static DeviceType getById(int id) {
    for (DeviceType sex : DeviceType.values()) {
      if (sex.id == id) {
        return sex;
      }
    }
    return null;
  }

  public static DeviceType getByName(String name) {
    for (DeviceType role : DeviceType.values()) {
      if (role.roleName.equalsIgnoreCase(name)) {
        return role;
      }
    }
    return null;
  }
  public int getId(){return this.id;}
}
