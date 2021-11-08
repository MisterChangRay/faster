package com.github.misterchangray.common.base;

public class BaseResponse<T> {
    private String serialNumber;
    private String code;
    private String msg;
    private long timestamp;

    private T data;


    public static <T> BaseResponse<T> ofSuccess(ResEnum resEnum, T data) {
        return BaseResponse.build(resEnum, data);
    }

    public static <T> BaseResponse<T> ofSuccess(T data) {
        return BaseResponse.build(BaseEnum.SUCCESS, data);
    }


    public static <T>  BaseResponse<T> ofFail(ResEnum resEnum) {
        return BaseResponse.<T>build(resEnum, null);
    }

    public boolean isSuccess() {
        return BaseEnum.SUCCESS.getCode().equals(this.code);
    }

    private static <K> BaseResponse<K> build(ResEnum resEnum, K data) {
        BaseResponse<K> res = new BaseResponse<K>();
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
