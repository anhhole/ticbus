package com.ticbus.backend.common.enumeration;

public enum EnumSeatBusType {

    SEAT_16("XE 16 CHỖ"),

    SEAT_32("XE 32 CHỖ"),

    SEAT_64("XE 64 CHỖ");

    private String label;

    private EnumSeatBusType(String label){
        this.label = label;
    }
}
