package com.github.misterchangray.financial.v001.pojo.request;


import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 * 财务账户变动请求
 *
 *
 * **/
public class FinancialChangesRecordRequest implements Serializable {

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

    // 变动金额
    private BigDecimal amount;


    // 变动朔源ID, 即发起变动这个行为的ID
    // 可以是订单Id, 提现Id, 退款Id
    private String sourceId;


    // 变动详情, 可用于备注
    private String remark;

    // 创建时间
    private long createTime;

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


}
