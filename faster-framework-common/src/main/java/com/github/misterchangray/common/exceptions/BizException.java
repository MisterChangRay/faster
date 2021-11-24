package com.github.misterchangray.common.exceptions;

import com.github.misterchangray.common.base.ResEnum;

public class BizException extends RuntimeException {
    private ResEnum res;

    public BizException(ResEnum resEnum) {
        super(resEnum.getMsg());
        this.res = resEnum;
    }

    public ResEnum getRes() {
        return res;
    }

    public void setRes(ResEnum res) {
        this.res = res;
    }
}
