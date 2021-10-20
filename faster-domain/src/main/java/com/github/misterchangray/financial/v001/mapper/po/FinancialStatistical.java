package com.github.misterchangray.financial.v001.mapper.po;


import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * 财务账户聚合统计
 *
 * 财务账户将会以 小时, 天, 月, 年 为维度进行聚合统计
 *
 * 小时, 天, 月 这三个统计以用户创建时间作为结算周期,
 * 比如用户在 2020-02-02 13:56:13 时创建账户, 用户将在以下时间进行账户结算统计:
 * 2020-02-02 14:56:13 时触发小时统计
 * 2020-02-03 13:56:13 时触发天统计
 * 2020-03-02 13:56:13 时触发月统计
 *
 * 年统计将会在年底直接触发, 同时统计所有账户
 *
 *
 * **/
public class FinancialStatistical {

    /**
     * ID
     */
    private String id;

    // 账户表ID
    private String financialAccountId;

    // 账户用户ID
    private String userId;

    // 账户姓名
    private String name;

    // 账户手机号
    private String phone;

    // 总余额
    private BigDecimal balance;

    // 总冻结金额
    private BigDecimal freeze;

    // 总提现金额
    private BigDecimal withdrew;

    // 总充值金额
    private BigDecimal recharge;

    // 开始统计时间
    private Date scanTime;

    // 统计结束时间
    private Date finishTime;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;

    // 备注
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFreeze() {
        return freeze;
    }

    public void setFreeze(BigDecimal freeze) {
        this.freeze = freeze;
    }

    public BigDecimal getWithdrew() {
        return withdrew;
    }

    public void setWithdrew(BigDecimal withdrew) {
        this.withdrew = withdrew;
    }

    public BigDecimal getRecharge() {
        return recharge;
    }

    public void setRecharge(BigDecimal recharge) {
        this.recharge = recharge;
    }

    public Date getScanTime() {
        return scanTime;
    }

    public void setScanTime(Date scanTime) {
        this.scanTime = scanTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
