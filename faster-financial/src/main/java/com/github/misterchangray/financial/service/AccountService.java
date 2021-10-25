package com.github.misterchangray.financial.service;

import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;

import java.util.List;

public interface AccountService {

    BaseResponse<FinancialAccount> getUserFinancialAccount(String id);

    BaseResponse<List<FinancialAccount>> getUserFinancialAccount() ;

    BaseResponse<List<FinancialAccount>> updateUserFinancialAccount();

    BaseResponse<List<FinancialAccount>> changeBalanceUserFinancialAccount() ;

    BaseResponse<List<FinancialAccount>> changeFreezeUserFinancialAccount();

}
