package com.github.misterchangray.common.base;

public class BaseResponse<T> {
    private String serialNumber;
    private String code;
    private String msg;
    private long timestamp;

    private T data;


    public static <T> BaseResponse ofSuccess(ResEnum resEnum, T data) {
        BaseResponse build = BaseResponse.build(resEnum, data);
        return build;
    }

    public static <T> BaseResponse ofSuccess(T data) {
        BaseResponse build = BaseResponse.build(BaseEnum.SUCCESS, data);
        return build;
    }


    public static <T>  BaseResponse ofFail(ResEnum resEnum) {
        BaseResponse build = BaseResponse.build(resEnum, null);
        return build;
    }

    public boolean isSuccess() {
        return BaseEnum.SUCCESS.getCode().equals(this.code);
    }

    private static <T> BaseResponse build(ResEnum resEnum, T data) {
        BaseResponse res = new BaseResponse();
        res.setTimestamp(System.currentTimeMillis());
        res.setCode(resEnum.getCode());;
        res.setMsg(resEnum.getMsg());;
        res.setData(data);
        return res;
    }


    public String getSerialNumber() {
        return serialNumber;
    }

    public BaseResponse<T> setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public String getCode() {
        return code;
    }

    public BaseResponse<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BaseResponse<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public BaseResponse<T> setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public T getData() {
        return data;
    }

    public BaseResponse<T> setData(T data) {
        this.data = data;
        return this;
    }
}
