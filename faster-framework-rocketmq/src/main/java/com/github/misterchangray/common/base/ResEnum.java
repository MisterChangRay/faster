package com.github.misterchangray.common.base;

public interface ResEnum {
    String getCode();
    String getMsg();

    default boolean isSuccess() {
        return "0000".equals(this.getCode());
    }
}
