package com.github.misterchangray.financial.v001.pojo.request;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;


/**
 *
 * 财务账户表,
 *
 * 所有金额都使用 decimal, 计算精度保留 6 位
 *
 * **/
public class FinancialAccountRequest {
    // 金融账户ID
    private String id;

    // 账户用户ID
    private String userId;

    // 账户姓名
    @NotNull
    @Pattern(regexp = "^.{2,100}$", message = "用户名,2-100个字符")
    private String name;

    // 账户手机号
    @NotNull
    @Pattern(regexp = "^1\\d{10}$", message = "手机号")
    private String phone;

    // 1 启动 2禁用
    private int status;

    // 创建时间
    private long createTime;

    // 更新时间
    private long updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
