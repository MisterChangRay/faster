package com.github.misterchangray.financial.service;

import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;
import com.github.misterchangray.financial.v001.mapper.po.FinancialChangesRecord;
import com.github.misterchangray.financial.v001.pojo.request.FinancialChangesRecordRequest;

import java.util.List;

public interface AccountService {

    /**
     * 开始初始化影子账号
     * 这里将会自动初始化影子账户
     * 根据账户活跃度 优先级进行初始化
     */
    void startInitShadowAccount();


    /**
     * 记录账户变动详情
     * @param financialChangesRecord
     * @return
     */
    BaseResponse<Boolean> record(FinancialChangesRecordRequest ...financialChangesRecord);

    BaseResponse<List<FinancialAccount>> getUserFinancialAccount(String ...ids) ;

    BaseResponse<List<FinancialAccount>> updateUserFinancialAccount();

    BaseResponse<List<FinancialAccount>> changeBalanceUserFinancialAccount() ;

    BaseResponse<List<FinancialAccount>> changeFreezeUserFinancialAccount();

}
