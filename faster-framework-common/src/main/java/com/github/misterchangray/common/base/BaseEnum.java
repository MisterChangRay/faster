package com.github.misterchangray.common.base;

public enum BaseEnum implements ResEnum {
    SUCCESS("0000", "SUCCESS"),
    FAIL("0000", "FAIL"),
    ;

    private String code;
    private String msg;

    BaseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.getCode();
    }

    @Override
    public String getMsg() {
        return getMsg();
    }


}
