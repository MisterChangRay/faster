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

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
