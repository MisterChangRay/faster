package com.github.misterchangray.common.base;


import java.util.Objects;

public class BaseResponse<T> {
    private String code;
    private String msg;

    private T data;


    public static <T>  BaseResponse ofSuccess(ResEnum resEnum, T data) {
        BaseResponse build = BaseResponse.build(resEnum, data);
        return build;
    }

    public BaseResponse ofSuccess(T data) {
        BaseResponse build = BaseResponse.build(BaseEnum.SUCCESS, data);
        return build;
    }


    public BaseResponse ofFail(ResEnum resEnum) {
        BaseResponse build = BaseResponse.build(resEnum, null);
        return build;
    }

    public boolean isSuccess() {
        return BaseEnum.SUCCESS.getCode().equals(this.code);
    }

    private static <T> BaseResponse build(ResEnum resEnum, T data) {
        BaseResponse res = new BaseResponse();
        res.code = resEnum.getCode();
        res.msg = resEnum.getMsg();
        res.data = data;
        return res;
    }

}
