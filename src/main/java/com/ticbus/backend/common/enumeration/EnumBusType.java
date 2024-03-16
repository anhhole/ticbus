package com.ticbus.backend.common.enumeration;


public enum EnumBusType {

    SEAT("GHẾ NGỒI"),

    BED_SEAT("GHẾ GIƯỜNG NẰM");

    private String label;

    private EnumBusType(String label){
        this.label = label;
    }
}
