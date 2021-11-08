package com.github.misterchangray.common.base;

public enum BaseEnum implements ResEnum {
    SUCCESS("0000", "SUCCESS"),
    FAIL("9999", "FAIL"),

    INVALID_REQUEST("0001", "INVALID_REQUEST"),
    INVALID_PARAM("0002", "INVALID_PARAM"),
    INVALID_USER("0003", "INVALID_USER"),
    INVALID_SESSION("0004", "INVALID_SESSION"),
    INVALID_ACCOUNT("0005", "INVALID_ACCOUNT"),

    ;

    private String code;
    private String msg;

    BaseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }


}
