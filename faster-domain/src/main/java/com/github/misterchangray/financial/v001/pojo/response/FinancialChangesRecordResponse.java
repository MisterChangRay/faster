package com.github.misterchangray.financial.v001.pojo.response;


import com.github.misterchangray.common.base.BaseResponse;

import java.math.BigDecimal;


/**
 *
 * 财务账户变动响应
 *
 *
 * **/
public class FinancialChangesRecordResponse extends BaseResponse<String> {

    /**
     * 请求流水号
     * 由业务方指定
     */
    private String requestSerialNumber;


    // 账户表ID
    private String financialAccountId;


    public String getRequestSerialNumber() {
        return requestSerialNumber;
    }

    public void setRequestSerialNumber(String requestSerialNumber) {
        this.requestSerialNumber = requestSerialNumber;
    }

    public String getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(String financialAccountId) {
        this.financialAccountId = financialAccountId;
    }
}
