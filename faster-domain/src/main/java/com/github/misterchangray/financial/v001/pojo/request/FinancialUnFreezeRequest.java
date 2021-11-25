package com.github.misterchangray.financial.v001.pojo.request;


import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 *
 * 财务余额冻结
 *
 * **/
public class FinancialUnFreezeRequest implements Serializable {

    /**
     * 流水号
     * 由业务方指定
     */
    private String serialNumber;

    // 冻结ID
    private String freezeId;

    // 解冻金额
    private BigDecimal unfreezeAmount;

    // 变动朔源ID, 即发起变动这个行为的ID
    // 可以是订单Id, 提现Id, 退款Id
    private String sourceId;

    // 变动详情, 可用于备注
    private String remark;



}
