package com.ticbus.backend.common.enumeration;

/**
 * @author AnhLH
 */
public enum EnumStatus {
  ENABLE(1, "1"),
  DISABLE(2, "2"),
  DELETE(3, "3");

  private int id;
  private String name;

  EnumStatus(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static EnumStatus getById(int id) {
    for (EnumStatus sex : EnumStatus.values()) {
      if (sex.id == id) {
        return sex;
      }
    }
    return null;
  }

  public static EnumStatus getByName(String name) {
    for (EnumStatus sex : EnumStatus.values()) {
      if (sex.name.equalsIgnoreCase(name)) {
        return sex;
      }
    }
    return null;
  }
  public int getId(){return this.id;}
}
