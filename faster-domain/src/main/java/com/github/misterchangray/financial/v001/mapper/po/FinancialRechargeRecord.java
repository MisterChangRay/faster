package com.github.misterchangray.financial.v001.mapper.po;


import java.math.BigDecimal;


/**
 *
 * 财务账户表 充值记录表
 *
 *
 * **/
public class FinancialRechargeRecord {
    private String id;

    // 账户用户ID
    private String userId;

    // 账户姓名
    private String name;

    // 账户手机号
    private String phone;

    // 充值金额
    private BigDecimal amount;

    // 提现状态 1待付款  2充值成功 3充值失败
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
