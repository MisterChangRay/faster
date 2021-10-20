package com.github.misterchangray.financial.v001.mapper.po;


import java.math.BigDecimal;


/**
 *
 * 财务支付表
 *
 * 记录支付信息
 *
 *
 * **/
public class FinancialPaymentRecord {

    /**
     * 支付表ID
     */
    private String id;

    /**
     * 流水号
     * 由业务方指定
     */
    private String serialNumber;

    // 账户表ID
    private String financialAccountId;

    // 账户用户ID
    private String userId;

    // 账户姓名
    private String name;

    // 账户手机号
    private String phone;

    // 支付金额 分
    private long amount;

    /**
     * 支付方式
     * 1 -  99 余额支付
     * 1 余额支付
     *
     * 100 - 199
     * 100 支付宝支付
     *
     * 200 - 299
     * 200 微信支付
     * 201 微信信用分支付
     * 202 微信现金支付
     *
     *
     *
     */
    private int type;


    // 朔源ID, 即发起变动这个行为的ID
    // 可以是订单Id, 提现Id, 退款Id
    private String orderId;

    /**
     * 支付状态
     * 1 待支付
     * 2 支付成功
     * 3 支付失败
     */
    private int status;


    // 备注
    private String remark;

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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(String financialAccountId) {
        this.financialAccountId = financialAccountId;
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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
