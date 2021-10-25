package com.github.misterchangray.financial.service.impl;

import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.financial.service.AccountService;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Override
    public BaseResponse<FinancialAccount> getUserFinancialAccount(String id) {


        return null;
    }

    @Override
    public BaseResponse<List<FinancialAccount>> getUserFinancialAccount() {
        return null;
    }

    @Override
    public BaseResponse<List<FinancialAccount>> updateUserFinancialAccount() {
        return null;
    }

    @Override
    public BaseResponse<List<FinancialAccount>> changeBalanceUserFinancialAccount() {
        return null;
    }

    @Override
    public BaseResponse<List<FinancialAccount>> changeFreezeUserFinancialAccount() {
        return null;
    }
}
