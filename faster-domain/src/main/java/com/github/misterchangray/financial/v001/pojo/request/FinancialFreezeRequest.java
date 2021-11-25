package com.github.misterchangray.financial.v001.pojo.request;


import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 *
 * 财务余额冻结
 * 在对业务进行提现, 转账, 付款等支出操作前, 先进行订单的金额冻结。冻结成功后即可进行业务处理
 *
 *
 * **/
public class FinancialFreezeRequest implements Serializable {

    /**
     * 流水号
     * 由业务方指定
     */
    private String serialNumber;

    // 账户表ID
    private String financialAccountId;

    // 冻结金额
    private BigDecimal amount;

    // 变动朔源ID, 即发起变动这个行为的ID
    // 可以是订单Id, 提现Id, 退款Id
    private String sourceId;

    // 变动详情, 可用于备注
    private String remark;

    // 创建时间
    private long createTime;

    // 业务变动类型, 由业务端提供
    private OperationType bizType;

    // 账户解冻时操作, 默认未回滚
    private OperationUnFreeze operationUnFreeze;


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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
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

    public OperationType getBizType() {
        return bizType;
    }

    public void setBizType(OperationType bizType) {
        this.bizType = bizType;
    }

    public OperationUnFreeze getOperationUnFreeze() {
        return operationUnFreeze;
    }

    public void setOperationUnFreeze(OperationUnFreeze operationUnFreeze) {
        this.operationUnFreeze = operationUnFreeze;
    }
}
