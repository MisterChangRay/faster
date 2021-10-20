package com.github.misterchangray.financial.v001.mapper.po;


import java.math.BigDecimal;


/**
 *
 * 财务账户变动记录
 *
 *
 * **/
public class FinancialChangesRecord {

    /**
     * 账户变动记录ID
     */
    private String id;

    /**
     * 流水号
     * 由业务方指定
     */
    private String serialNumber;

    // 账户表ID
    private String financialAccountId;

    /**
     *  账本ID
     *  某些情况下某些明细不想入余额 或者其他业务进行统计
     */
    private int booksId;

    // 账户用户ID
    private String userId;

    // 账户姓名
    private String name;

    // 账户手机号
    private String phone;

    // 变动金额
    private BigDecimal amount;

    // 变动后账户余额
    private BigDecimal afterBalance;

    // 变动后冻结余额
    private BigDecimal afterFreeze;

    // 变动朔源ID, 即发起变动这个行为的ID
    // 可以是订单Id, 提现Id, 退款Id
    private String sourceId;

    /**
     *  变动类型
     *  1 - 999 其他变动
     *  1 后台账户充值
     *  2 后台账户扣减
     *  3 保证金充值
     *  4 保证金撤销
     *
     *
     *  1000 - 1999 订单变动
     *  1000 余额支付
     *  1002 用户取消订单退款
     *  1003 订单分润
     *  1004 订单分润冲正
     *
     *
     *  2000 - 2999 充值提现变动
     *  2000 充值
     *  2001 提现冻结
     *  2002 提现成功余额扣减
     *  2003 提现失败余额冲正
     *
     *
     *
     */
    private int type;

    /**
     * 变动状态
     * 1 已结算到余额
     * 2 未结算
     */
    private int status;


    // 变动详情, 可用于备注
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

    public int getBooksId() {
        return booksId;
    }

    public void setBooksId(int booksId) {
        this.booksId = booksId;
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

    public BigDecimal getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(BigDecimal afterBalance) {
        this.afterBalance = afterBalance;
    }

    public BigDecimal getAfterFreeze() {
        return afterFreeze;
    }

    public void setAfterFreeze(BigDecimal afterFreeze) {
        this.afterFreeze = afterFreeze;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
